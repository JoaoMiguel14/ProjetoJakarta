package br.upf.trabalhofinal.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "produtos")
public class ProdutoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
 
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descricao")
    private String descricao;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "preco")
    private Number preco;
    
    @Basic(optional = false)
    @Column(name = "qtdEstoque")
    private Number qtdEstoque;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "unidadeEmbalagem")
    private String unidadeEmbalagem;    
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "categoria")
    private String categoria;   

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datahorareg")
    private Date datahorareg;  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Number getPreco() {
        return preco;
    }

    public void setPreco(Number preco) {
        this.preco = preco;
    }

    public Number getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Number qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public String getUnidadeEmbalagem() {
        return unidadeEmbalagem;
    }

    public void setUnidadeEmbalagem(String unidadeEmbalagem) {
        this.unidadeEmbalagem = unidadeEmbalagem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getDatahorareg() {
        return datahorareg;
    }

    public void setDatahorareg(Date datahorareg) {
        this.datahorareg = datahorareg;
    }
    
}
