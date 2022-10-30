package com.example.gamelogic.entities;

public interface IInteractable {
    public void OnHoverEnter();
    public void OnHoverExit();
    public void OnTouchDown();
    public void OnTouchUp();
    public void OnTouchMove(int x, int y);
}
