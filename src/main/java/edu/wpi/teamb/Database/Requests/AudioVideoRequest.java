package edu.wpi.teamb.Database.Requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "audiovideorequest", schema = "iter4")
@PrimaryKeyJoinColumn(
    name = "audiovideoRequestID",
    foreignKey = @ForeignKey(name = "AudioVideoRequestIDKey_iter4"))
public class AudioVideoRequest extends GeneralRequest {

  @Column(name = "avtype", length = 40)
  @Getter
  @Setter
  private String AVType;

  public AudioVideoRequest() {};
}
