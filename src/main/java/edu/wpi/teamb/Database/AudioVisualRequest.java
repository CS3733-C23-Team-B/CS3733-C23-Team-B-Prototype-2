package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "audiovisualrequest")
@PrimaryKeyJoinColumn(
        name = "audiovisualRequestID",
        foreignKey = @ForeignKey(name = "AudioVisualRequestIDKey"))
public class AudioVisualRequest extends GeneralRequest{

    @Column(name = "avtype", length = 40)
    @Getter
    @Setter
    private String avType;

    public AudioVisualRequest(){};
}
