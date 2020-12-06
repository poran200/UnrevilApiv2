package com.unriviel.api.model.metadata;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Coordinate implements Serializable {
  private   int x;
   private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}