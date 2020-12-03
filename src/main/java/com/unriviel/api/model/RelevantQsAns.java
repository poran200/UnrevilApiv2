package com.unriviel.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode()
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RelevantQsAns  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private RelevantQuestion question;
    private String answer;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Profile profile;

    public RelevantQsAns(RelevantQuestion question, String answer, Profile profile) {
        this.question = question;
        this.answer = answer;
        this.profile = profile;
    }
}
