package protocol;


public class RussianLetterHelperUM {

    public static byte setRussianLetter(char c) {
        byte b = (byte) c;

        switch (c) {
            case 'А':
                b = -64;
                break;
            case 'Б':
                b = -63;
                break;
            case 'В':
                b = -62;
                break;
            case 'Г':
                b = -61;
                break;
            case 'Д':
                b = -60;
                break;
            case 'Е':
                b = -59;
                break;
            case 'Ж':
                b = -58;
                break;
            case 'З':
                b = -57;
                break;
            case 'И':
                b = -56;
                break;
            case 'Й':
                b = -55;
                break;
            case 'К':
                b = -54;
                break;
            case 'Л':
                b = -53;
                break;
            case 'М':
                b = -52;
                break;
            case 'Н':
                b = -51;
                break;
            case 'О':
                b = -50;
                break;
            case 'П':
                b = -49;
                break;
            case 'Р':
                b = -48;
                break;
            case 'С':
                b = -47;
                break;
            case 'Т':
                b = -46;
                break;
            case 'У':
                b = -45;
                break;
            case 'Ф':
                b = -44;
                break;
            case 'Х':
                b = -43;
                break;
            case 'Ц':
                b = -42;
                break;
            case 'Ч':
                b = -41;
                break;
            case 'Ш':
                b = -40;
                break;
            case 'Щ':
                b = -39;
                break;
            case 'Ъ':
                b = -38;
                break;
            case 'Ы':
                b = -37;
                break;
            case 'Ь':
                b = -36;
                break;
            case 'Э':
                b = -35;
                break;
            case 'Ю':
                b = -34;
                break;
            case 'Я':
                b = -33;
                break;
            case 'а':
                b = -32;
                break;
            case 'б':
                b = -31;
                break;
            case 'в':
                b = -30;
                break;
            case 'г':
                b = -29;
                break;
            case 'д':
                b = -28;
                break;
            case 'е':
                b = -27;
                break;
            case 'ж':
                b = -26;
                break;
            case 'з':
                b = -25;
                break;
            case 'и':
                b = -24;
                break;
            case 'й':
                b = -23;
                break;
            case 'к':
                b = -22;
                break;
            case 'л':
                b = -21;
                break;
            case 'м':
                b = -20;
                break;
            case 'н':
                b = -19;
                break;
            case 'о':
                b = -18;
                break;
            case 'п':
                b = -17;
                break;
            case 'р':
                b = -16;
                break;
            case 'с':
                b = -15;
                break;
            case 'т':
                b = -14;
                break;
            case 'у':
                b = -13;
                break;
            case 'ф':
                b = -12;
                break;
            case 'х':
                b = -11;
                break;
            case 'ц':
                b = -10;
                break;
            case 'ч':
                b = -9;
                break;
            case 'ш':
                b = -8;
                break;
            case 'щ':
                b = -7;
                break;
            case 'ъ':
                b = -6;
                break;
            case 'ы':
                b = -5;
                break;
            case 'ь':
                b = -4;
                break;
            case 'э':
                b = -3;
                break;
            case 'ю':
                b = -2;
                break;
            case 'я':
                b = -1;
                break;
            case 'Ё':
                b = -88;
                break;
            case 'ё':
                b = -72;
                break;
        }

        return b;
    }

    public static char getRussianLetter(byte b) {
        char c = (char) b;

        switch (b) {
            case -64:
                c = 'А';
                break;
            case -63:
                c = 'Б';
                break;
            case -62:
                c = 'В';
                break;
            case -61:
                c = 'Г';
                break;
            case -60:
                c = 'Д';
                break;
            case -59:
                c = 'Е';
                break;
            case -58:
                c = 'Ж';
                break;
            case -57:
                c = 'З';
                break;
            case -56:
                c = 'И';
                break;
            case -55:
                c = 'Й';
                break;
            case -54:
                c = 'К';
                break;
            case -53:
                c = 'Л';
                break;
            case -52:
                c = 'М';
                break;
            case -51:
                c = 'Н';
                break;
            case -50:
                c = 'О';
                break;
            case -49:
                c = 'П';
                break;
            case -48:
                c = 'Р';
                break;
            case -47:
                c = 'С';
                break;
            case -46:
                c = 'Т';
                break;
            case -45:
                c = 'У';
                break;
            case -44:
                c = 'Ф';
                break;
            case -43:
                c = 'Х';
                break;
            case -42:
                c = 'Ц';
                break;
            case -41:
                c = 'Ч';
                break;
            case -40:
                c = 'Ш';
                break;
            case -39:
                c = 'Щ';
                break;
            case -38:
                c = 'Ъ';
                break;
            case -37:
                c = 'Ы';
                break;
            case -36:
                c = 'Ь';
                break;
            case -35:
                c = 'Э';
                break;
            case -34:
                c = 'Ю';
                break;
            case -33:
                c = 'Я';
                break;
            case -32:
                c = 'а';
                break;
            case -31:
                c = 'б';
                break;
            case -30:
                c = 'в';
                break;
            case -29:
                c = 'г';
                break;
            case -28:
                c = 'д';
                break;
            case -27:
                c = 'е';
                break;
            case -26:
                c = 'ж';
                break;
            case -25:
                c = 'з';
                break;
            case -24:
                c = 'и';
                break;
            case -23:
                c = 'й';
                break;
            case -22:
                c = 'к';
                break;
            case -21:
                c = 'л';
                break;
            case -20:
                c = 'м';
                break;
            case -19:
                c = 'н';
                break;
            case -18:
                c = 'о';
                break;
            case -17:
                c = 'п';
                break;
            case -16:
                c = 'р';
                break;
            case -15:
                c = 'с';
                break;
            case -14:
                c = 'т';
                break;
            case -13:
                c = 'у';
                break;
            case -12:
                c = 'ф';
                break;
            case -11:
                c = 'х';
                break;
            case -10:
                c = 'ц';
                break;
            case -9:
                c = 'ч';
                break;
            case -8:
                c = 'ш';
                break;
            case -7:
                c = 'щ';
                break;
            case -6:
                c = 'ъ';
                break;
            case -5:
                c = 'ы';
                break;
            case -4:
                c = 'ь';
                break;
            case -3:
                c = 'э';
                break;
            case -2:
                c = 'ю';
                break;
            case -1:
                c = 'я';
                break;
            case -88:
                c = 'Ё';
                break;
            case -72:
                c = 'ё';
                break;
        }

        return c;
    }
}
