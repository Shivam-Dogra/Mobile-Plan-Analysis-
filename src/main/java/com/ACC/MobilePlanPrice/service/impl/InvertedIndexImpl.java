package com.ACC.MobilePlanPrice.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ACC.MobilePlanPrice.model.Index;

@Service
public class InvertedIndexImpl {
    
    private static class AVLNode {
        AVLNode left, right;
        int height;
        String key;
        AVLIndex index;
        String fileName;

        AVLNode(String key, int pageIndex, int position, String fileName) {
            this.key = key;
            this.height = 1;
            this.index = new AVLIndex(pageIndex, position);
            this.fileName = fileName;
        }
    }

    private static class AVLIndex {
        int pageIndex;
        int position;

        AVLIndex(int pageIndex, int position) {
            this.pageIndex = pageIndex;
            this.position = position;
        }
    }

    private AVLNode root;

    public void buildIndex(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder path: " + folderPath);
        }

        Map<String, Integer> fileIndexMap = new HashMap<String, Integer>();
        int pageIndex = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                fileIndexMap.put(file.getName(), pageIndex);
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    int position = 0;
                    while ((line = br.readLine()) != null) {
                        String[] words = line.split("\\s+");
                        for (String word : words) {
                            root = insert(root, word.toLowerCase(), pageIndex, position++, file.getName());
                        }
                    }
                    pageIndex++;
                } catch (IOException e) {
                    System.err.println("Error reading file: " + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
    }


    private AVLNode insert(AVLNode node, String word, int pageIndex, int position, String fileName) {
        if (node == null) {
            return new AVLNode(word, pageIndex, position, fileName);
        }

        if (word.compareTo(node.key) < 0) {
            node.left = insert(node.left, word, pageIndex, position, fileName);
        } else if (word.compareTo(node.key) > 0) {
            node.right = insert(node.right, word, pageIndex, position, fileName);
        } else {
            node.index.pageIndex = pageIndex;
            node.index.position = position;
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1 && word.compareTo(node.left.key) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && word.compareTo(node.right.key) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && word.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && word.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private int height(AVLNode node) {
        return (node != null) ? node.height : 0;
    }

    private int getBalance(AVLNode node) {
        return (node != null) ? height(node.left) - height(node.right) : 0;
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public List<Index> searchKeyword(String keyword) {
        List<Index> occurrences = new ArrayList<>();
        AVLNode node = search(root, keyword.toLowerCase());
        if (node == null) {
            System.out.println("Keyword not found: " + keyword);
            return occurrences;
        }

        collectOccurrences(node, occurrences);
        return occurrences;
    }

    private AVLNode search(AVLNode node, String keyword) {
        while (node != null) {
            int comparison = keyword.compareTo(node.key);
            if (comparison == 0) {
                return node;
            } else if (comparison < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    private void collectOccurrences(AVLNode node, List<Index> occurrences) {
        if (node != null) {
            collectOccurrences(node.left, occurrences);
            occurrences.add(new Index(node.index.pageIndex, node.index.position, node.fileName));
            collectOccurrences(node.right, occurrences);
        }
    }
}
