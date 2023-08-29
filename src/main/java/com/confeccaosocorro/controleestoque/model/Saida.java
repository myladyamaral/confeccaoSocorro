package com.confeccaosocorro.controleestoque.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(Movimento.SAIDA)
public class Saida extends Movimento{

}
