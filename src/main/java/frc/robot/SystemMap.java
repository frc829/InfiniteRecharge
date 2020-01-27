package frc.robot;

public class SystemMap{

    class Drive{
        public static final int FLTHRUSTER = 1;
        public static final int FRTHRUSTER = 2;
        public static final int BLTHRUSTER = 3;
        public static final int BRTHRUSTER = 4;

        public static final int PCM = 1;
        public static final int TRANS_CHANNEL_ON = 1;
        public static final int TRANS_CHANNEL_OFF = 0;
    }

    class Blaster{
        public static final int TOPBLASTER = 5;
        public static final int BOTBLASTER = 6;
        public static final int TILT = 7;
    }

    class Boost{
        public static final int BOOST = 8;
    }

    class Pez{
        public static final int PICKUP = 9;
        public static final int INTAKELEFT = 10;
        public static final int INTAKERIGHT = 11;
        public static final int BELTRIGHT = 12;
        public static final int BELTLEFT = 13;
    }



}