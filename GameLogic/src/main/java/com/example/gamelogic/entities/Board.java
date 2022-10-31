package com.example.gamelogic.entities;

import com.example.engine.IEngine;

public class Board extends Entity{
    int[][] board;

    public Board(IEngine engine, int rows, int cols) {
        super(engine);
        board = new int[rows][cols];
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {

    }

    @Override
    public void OnPointerDown(int x, int y) {

    }

    @Override
    public void OnPointerUp(int x, int y) {

    }

    @Override
    public void OnPointerMove(int x, int y) {

    }
}
