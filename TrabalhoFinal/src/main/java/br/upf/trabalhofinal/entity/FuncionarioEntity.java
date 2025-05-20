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
@Table(name = "funcionarios")
public class FuncionarioEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "nome")
    private String nome;
    
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "dataNascimento")
    private Date dataNascimento;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cpf")
    private String cpf;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "idade")
    private Integer idade;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cargo")
    private String cargo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "genero")
    private String genero;
    
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public Date getDatahorareg() {
        return datahorareg;
    }

    public void setDatahorareg(Date datahorareg) {
        this.datahorareg = datahorareg;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FuncionarioEntity other = (FuncionarioEntity) obj;
        return this.id == other.id;
    }
    
}
