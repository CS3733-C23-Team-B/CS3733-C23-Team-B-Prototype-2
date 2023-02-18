package edu.wpi.teamb.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "audiovideorequest")
@PrimaryKeyJoinColumn(
    name = "audiovideoRequestID",
    foreignKey = @ForeignKey(name = "AudioVideoRequestIDKey"))
public class AudioVideoRequest extends GeneralRequest {

  @Column(name = "avtype", length = 40)
  @Getter
  @Setter
  private String AVType;

  public AudioVideoRequest() {};
}
