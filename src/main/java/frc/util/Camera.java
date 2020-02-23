package frc.util;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera{

  private UsbCamera camera;

  public Camera() {

    this.setCamera(camera);;
    this.getCamera().setVideoMode(PixelFormat.kYUYV, 160, 120, 15);

  }

  
  public UsbCamera getCamera() {
    return this.camera;
  }

  public void setCamera(UsbCamera camera) {
    this.camera = camera;
  }


}