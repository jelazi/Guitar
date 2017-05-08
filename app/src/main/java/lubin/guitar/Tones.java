package lubin.guitar;

import android.widget.Switch;

/**
 * Created by Lubin on 23.4.2017.
 * Rozpeti tonu
 */



final class Tones {

//    modulace tonu
    final float string64 = 1.443f;
    final float string63 = 1.361f;
    final float string62 = 1.285f;
    final float string61 = 1.212f;
    final float string60 = 1.145f;
    final float string54 = 1.081f;
    final float string53 = 1.020f;
    final float string52 = 0.963f;
    final float string51 = 0.908f;
    final float string50 = 0.858f;
    final float string44 = 0.858f;
    final float string43 = 0.809f;
    final float string42 = 0.764f;
    final float string41 = 0.721f;
    final float string40 = 0.680f;
    final float string34 = 0.643f;
    final float string33 = 0.607f;
    final float string32 = 0.573f;
    final float string31 = 0.541f;
    final float string30 = 0.510f;
    final float string24 = 0.482f;
    final float string23 = 0.454f;
    final float string22 = 0.429f;
    final float string21 = 0.405f;
    final float string20 = 0.382f;
    final float string14 = 0.361f;
    final float string13 = 0.340f;
    final float string12 = 0.321f;
    final float string11 = 0.303f;
    final float string10 = 0.286f;


    final float Gis4 = string64;
    final float G4 = string63;
    final float Fis4 = string62;
    final float F4 = string61;
    final float E4 = string60;
    final float Dis4 = string54;
    final float D4 = string53;
    final float Cis4 = string52;
    final float C4 = string51;
    final float B3 = string50;
    final float B32 = string44;
    final float Ais3 = string43;
    final float A3 = string42;
    final float Gis3 = string41;
    final float G3 = string40;
    final float Fis3 = string34;
    final float F3 = string33;
    final float E3 = string32;
    final float Dis3 = string31;
    final float D3 = string30;
    final float Cis3 = string24;
    final float C3 = string23;
    final float B2 = string22;
    final float Ais2 = string21;
    final float A2 = string20;
    final float Gis2 = string14;
    final float G2 = string13;
    final float Fis2 = string12;
    final float F2 = string11;
    final float E2 = string10;
    final float silent = 0;




// vrátí zpět struny akordu
    public float[] getAkord(String akord){
        float[] akordString = new float[6];

        switch (akord){
            case "Cdur":{
                akordString[0] = G2;
                akordString[1] = C3;
                akordString[2] = E3;
                akordString[3] = G3;
                akordString[4] = C4;
                akordString[5] = E4;
                break;
            }
            case "Ddur":{
                akordString[0] = silent;
                akordString[1] = silent;
                akordString[2] = D3;
                akordString[3] = A3;
                akordString[4] = D4;
                akordString[5] = Fis4;
                break;
            }
            case "Edur":{
                akordString[0] = E2;
                akordString[1] = B2;
                akordString[2] = E3;
                akordString[3] = Gis3;
                akordString[4] = B3;
                akordString[5] = E4;
                break;

            }
            case "Fdur":{
                akordString[0] = F2;
                akordString[1] = C3;
                akordString[2] = F3;
                akordString[3] = A3;
                akordString[4] = C4;
                akordString[5] = F4;
                break;
            }
            case "Gdur":{
                akordString[0] = G2;
                akordString[1] = B2;
                akordString[2] = D3;
                akordString[3] = G3;
                akordString[4] = B3;
                akordString[5] = G4;
                break;

            }
            case "Adur":{
                akordString[0] = silent;
                akordString[1] = A2;
                akordString[2] = E3;
                akordString[3] = A3;
                akordString[4] = Cis4;
                akordString[5] = E4;
                break;
            }
            case "Bdur":{
                akordString[0] = silent;
                akordString[1] = Ais2;
                akordString[2] = F3;
                akordString[3] = Ais3;
                akordString[4] = D4;
                akordString[5] = F4;
                break;
            }
            case "Cmi":{
                akordString[0] = E2;
                akordString[1] = A2;
                akordString[2] = D3;
                akordString[3] = G3;
                akordString[4] = B3;
                akordString[5] = E4;
                break;
            }
            default:{
                akordString[0] = E2;
                akordString[1] = A2;
                akordString[2] = D3;
                akordString[3] = G3;
                akordString[4] = B3;
                akordString[5] = E4;
                break;
            }
        }
        return akordString;
    }





    public float getString14() {
        return string14;
    }

    public float getString13() {
        return string13;
    }

    public float getString12() {
        return string12;
    }

    public float getString11() {
        return string11;
    }

    public float getString10() {
        return string10;
    }

    public float getString24() {
        return string24;
    }

    public float getString23() {
        return string23;
    }

    public float getString22() {
        return string22;
    }

    public float getString21() {
        return string21;
    }

    public float getString20() {
        return string20;
    }

    public float getString34() {
        return string34;
    }

    public float getString33() {
        return string33;
    }

    public float getString32() {
        return string32;
    }

    public float getString31() {
        return string31;
    }

    public float getString30() {
        return string30;
    }

    public float getString44() {
        return string44;
    }

    public float getString43() {
        return string43;
    }

    public float getString42() {
        return string42;
    }

    public float getString41() {
        return string41;
    }

    public float getString40() {
        return string40;
    }

    public float getString54() {
        return string54;
    }

    public float getString53() {
        return string53;
    }

    public float getString52() {
        return string52;
    }

    public float getString51() {
        return string51;
    }

    public float getString50() {
        return string50;
    }

    public float getString64() {
        return string64;
    }

    public float getString63() {
        return string63;
    }

    public float getString62() {
        return string62;
    }

    public float getString61() {
        return string61;
    }

    public float getString60() {
        return string60;
    }

    public float getGis4() {
        return Gis4;
    }

    public float getG4() {
        return G4;
    }

    public float getFis4() {
        return Fis4;
    }

    public float getF4() {
        return F4;
    }

    public float getE4() {
        return E4;
    }

    public float getDis4() {
        return Dis4;
    }

    public float getD4() {
        return D4;
    }

    public float getCis4() {
        return Cis4;
    }

    public float getC4() {
        return C4;
    }

    public float getB3() {
        return B3;
    }

    public float getB32() {
        return B32;
    }

    public float getAis3() {
        return Ais3;
    }

    public float getA3() {
        return A3;
    }

    public float getGis3() {
        return Gis3;
    }

    public float getG3() {
        return G3;
    }

    public float getFis3() {
        return Fis3;
    }

    public float getF3() {
        return F3;
    }

    public float getE3() {
        return E3;
    }

    public float getDis3() {
        return Dis3;
    }

    public float getD3() {
        return D3;
    }

    public float getCis3() {
        return Cis3;
    }

    public float getC3() {
        return C3;
    }

    public float getB2() {
        return B2;
    }

    public float getAis2() {
        return Ais2;
    }

    public float getA2() {
        return A2;
    }

    public float getGis2() {
        return Gis2;
    }

    public float getG2() {
        return G2;
    }

    public float getFis2() {
        return Fis2;
    }

    public float getF2() {
        return F2;
    }

    public float getE2() {
        return E2;
    }

    public float getSilent() {
        return silent;
    }
}




