package com.confeccaosocorro.controleestoque.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(Movimento.ENTRADA)
public class Entrada extends Movimento{

}
