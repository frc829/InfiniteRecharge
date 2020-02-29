package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera{

  private UsbCamera camera;

  public Camera() {

    this.setCamera(CameraServer.getInstance().startAutomaticCapture());
    this.getCamera().setVideoMode(PixelFormat.kYUYV, 160, 120, 15);

  }

  
  public UsbCamera getCamera() {
    return this.camera;
  }

  public void setCamera(UsbCamera camera) {
    this.camera = camera;
  }


}