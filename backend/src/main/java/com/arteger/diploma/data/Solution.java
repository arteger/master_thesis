package com.arteger.diploma.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Solution {
    private LinkedList<PlayerTurnPair> turns = new LinkedList<>();
    private Map<String, Double> outcomes = new HashMap<>();
}
