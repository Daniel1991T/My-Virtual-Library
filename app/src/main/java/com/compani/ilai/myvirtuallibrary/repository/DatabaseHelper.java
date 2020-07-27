package com.compani.ilai.myvirtuallibrary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String ALL_BOOKS_TABLE = "ALL_BOOKS_TABLE";
    public static final String COLUMN_BOOK_ID = "COLUMN_BOOK_ID";
    public static final String COLUMN_BOOK_NAME = "COLUMN_BOOK_NAME";
    public static final String COLUMN_BOOK_AUTHOR = "COLUMN_BOOK_AUTHOR";
    public static final String COLUMN_BOOK_PAGES = "COLUMN_BOOK_PAGES";
    public static final String COLUMN_BOOK_GEN = "COLUMN_BOOK_GEN";
    public static final String COLUMN_BOOK_IMAGE_URL = "COLUMN_BOOK_IMAGE_URL";
    public static final String COLUMN_BOOK_DESCRIPTION = "COLUMN_BOOK_DESCRIPTION";
    public static final String COLUMN_ALL_TABLES = " (" + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BOOK_NAME + " TEXT, " + COLUMN_BOOK_AUTHOR +
            " TEXT, " + COLUMN_BOOK_PAGES + " INT, " + COLUMN_BOOK_GEN + " TEXT, " + COLUMN_BOOK_IMAGE_URL + " TEXT, " + COLUMN_BOOK_DESCRIPTION + " TEXT )";
    public static final String CURRENTLY_READING_BOOK_TABLE = "CURRENTLY_READING_BOOK_TABLE";
    public static final String WANT_TO_READ_BOOKS_TABLE = "WANT_TO_READ_BOOKS_TABLE";
    private static final String FAVORITE_BOOK_TABLE = "FAVORITE_BOOK_TABLE";
    public static final String ALREADY_READ_TABLE = "ALREADY_READ_TABLE";
    //TODO: change the db name book.db whit DATABASE_NAME when the app is finish
//    public static final String DATABASE_NAME = RegisterActivity.prefs.getString(LIBRARY_NAME_SP, null) + ".db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "book.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAllBooks = "CREATE TABLE IF NOT EXISTS " + ALL_BOOKS_TABLE + COLUMN_ALL_TABLES;
        db.execSQL(createTableAllBooks);

        String createTableCurrentlyReadingBook = "CREATE TABLE IF NOT EXISTS " + CURRENTLY_READING_BOOK_TABLE + " ("
                + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY)";
        db.execSQL(createTableCurrentlyReadingBook);

        String createTableWantToReadBooks = "CREATE TABLE IF NOT EXISTS " + WANT_TO_READ_BOOKS_TABLE + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY)";
        db.execSQL(createTableWantToReadBooks);

        String createTableFavoriteBooks = "CREATE TABLE IF NOT EXISTS " + FAVORITE_BOOK_TABLE + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY)";
        db.execSQL(createTableFavoriteBooks);

        String createTableAlreadyReadBooks = "CREATE TABLE IF NOT EXISTS " + ALREADY_READ_TABLE + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY)";
        db.execSQL(createTableAlreadyReadBooks);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK_NAME, "Deep Work");
        cv.put(COLUMN_BOOK_AUTHOR, "Cal Newport");
        cv.put(COLUMN_BOOK_PAGES, 304);
        cv.put(COLUMN_BOOK_GEN, "Dezvoltare personala");
        cv.put(COLUMN_BOOK_IMAGE_URL, "https://cdn4.libris.ro/img/pozeprod/59/1000/32/1258010-JVYC.jpg");
        cv.put(COLUMN_BOOK_DESCRIPTION, "IPOTEZA MUNCII PROFUNDE: abilitatea de a indeplini o munca profunda devine din ce in ce mai rara " +
                "si, implicit, din ce in ce mai valoroasa in economia noastra. " +
                "Drept urmare, cei foarte putini care cultiva si, mai apoi, transforma aceasta abilitate in esenta vietii lor profesionale, " +
                "vor prospera. Deep Work are doua teluri: primul este de a convinge cititorii ca ipoteza muncii profunde este adevarata. " +
                "Cel de‑al doilea, este de a-i invata pe cititori sa profite de aceasta realitate, antrenandu‑si mintea si transformandu‑si" +
                " obiceiurile de lucru prin plasarea muncii profunde in centrul vietii lor profesionale.");
        db.insert(ALL_BOOKS_TABLE, null, cv);

        cv.put(COLUMN_BOOK_NAME, "Cum Vad eu lumea");
        cv.put(COLUMN_BOOK_AUTHOR, "Albert Einstein");
        cv.put(COLUMN_BOOK_PAGES, 304);
        cv.put(COLUMN_BOOK_GEN, "Stiinta si filosofie");
        cv.put(COLUMN_BOOK_IMAGE_URL, "https://cdn4.libris.ro/img/pozeprod//59/1010/80/884804-TNIO.jpg");
        cv.put(COLUMN_BOOK_DESCRIPTION, "Devenita simbol al stiintei, mai mult, simbol al unei intelepciuni care depaseste frontierele stiintei, " +
                "ajungand la intrebarile esentiale despre ordinea universului si rostul nostru pe pamant, " +
                "figura lui Einstein continua sa exercite o fascinatie fara egal, atat asupra oamenilor de stiinta, " +
                "cat si asupra profanilor, cuceriti de clarviziunea, nonconformismul si umorul sau. " +
                "Cum vad eu lumea reuneste articole si conferinte ale lui Einstein, " +
                "definitorii pentru calea urmata in cercetarile sale stiintifice, pentru personalitatea si " +
                "viziunea lui asupra lumii, texte prin care savantul a urmarit sa se apropie de publicul larg, " +
                "fara a lasa in umbra rigoarea fizicianului.");
        db.insert(ALL_BOOKS_TABLE, null, cv);

        cv.put(COLUMN_BOOK_NAME, "Arta de a citi gandurile");
        cv.put(COLUMN_BOOK_AUTHOR, "Henrik Fexeus");
        cv.put(COLUMN_BOOK_PAGES, 264);
        cv.put(COLUMN_BOOK_GEN, "Psihologie");
        cv.put(COLUMN_BOOK_IMAGE_URL, "https://cdn4.libris.ro/img/pozeprod/59/1000/B6/833145-JVBN.jpg");
        cv.put(COLUMN_BOOK_DESCRIPTION, "Citirea gandurilor nu este un mit. Este o realitate. " +
                "Doar ca este putin diferita de ceea ce isi imagineaza majoritatea oamenilor. " +
                "Cu instrumentele pe care le ofera aceasta carte, vei afla imediat ce gandeste si " +
                "simte cealalta persoana si vei putea sa-i influentezi gandurile si sentimentele. " +
                "Cand vei observa expresiile afisate pe fata sa, ii vei descifra emotiile. " +
                "Vei putea detecta imediat orice lipsa de onestitate sau minciuna. Vei invata " +
                "sa fii atent la modul in care ceilalti isi folosesc limbajul corpului " +
                "si vocea atunci cand comunica. Folosind acelasi limbaj al corpului si " +
                "acelasi ton al vocii ca interlocutorul tau, vei obtine intotdeauna o comunicare limpede " +
                "si o intelegere precisa a lucrurilor pe care doresti sa le impartasesti: la o intalnire romantica, " +
                "la un interviu pentru angajare sau in familie.");
        db.insert(ALL_BOOKS_TABLE, null, cv);

        cv.put(COLUMN_BOOK_NAME, "Bun venit in univers");
        cv.put(COLUMN_BOOK_AUTHOR, "Neil Degrasse");
        cv.put(COLUMN_BOOK_PAGES, 480);
        cv.put(COLUMN_BOOK_GEN, "Astronomie");
        cv.put(COLUMN_BOOK_IMAGE_URL, "https://cdn.dc5.ro/img-prod/2326883-0.jpeg");
        cv.put(COLUMN_BOOK_DESCRIPTION, "Intr-un stil incitant, autorii povestesc cele mai recente descoperiri in astrofizica," +
                " purtandu-si cititorii de la sistemul nostru solar pana la cele mai indepartate regiuni ale Universului cunoscut: " +
                "Cum traiesc si cum mor stelele? De ce nu mai e Pluto planeta? Ce sanse sunt sa existe viata pe alte planete? " +
                "Cum a luat nastere Universul? Suntem singuri sau facem parte dintr-un multivers?");
        db.insert(ALL_BOOKS_TABLE, null, cv);

        cv.put(COLUMN_BOOK_NAME, "Origini");
        cv.put(COLUMN_BOOK_AUTHOR, "Dan Brown");
        cv.put(COLUMN_BOOK_PAGES, 600);
        cv.put(COLUMN_BOOK_GEN, "Literatura universala");
        cv.put(COLUMN_BOOK_IMAGE_URL, "https://cdn4.libris.ro/img/pozeprod/59/1010/33/1192949-IMSD.jpg");
        cv.put(COLUMN_BOOK_DESCRIPTION, "De aceasta data, profesorul Robert Langdon este invitat la Muzeul Guggenheim din Bilbao pentru a lua parte la ceremonia de dezvaluire a unei inventii care „va schimba fata stiintei pentru totdeauna“.\n" +
                "Gazda este fostul student al profesorului, milionarul Edmond Kirsch, ale carui realizari high-tech l-au facut celebru in toata lumea. El va prezenta pentru prima data descoperirea care raspunde la doua dintre intrebarile fundamentale ale omenirii.\n" +
                "Seara meticulos pregatita se transforma insa in haos, pretioasa descoperire a lui Kirsch risca sa fie pierduta pentru totdeauna, iar Langdon, insotit de eleganta directoare a muzeului, Ambra Vidal, este nevoit sa zboare la Barcelona pentru a localiza parola de decriptare a descoperirii lui Kirsch.\n" +
                "Strabatand coridoarele intunecate ale istoriei secrete si extremismului religios, cei doi trebuie sa faca fata unui inamic periculos, al carui singur scop este sa il reduca la tacere pe Edmond Kirsch.\n" +
                "Intr-o cursa contracronometru marcata de simboluri enigmatice si indicii oferite de arta moderna, Langdon si Vidal identifica, in cele din urma socanta descoperire a lui Kirsch si adevarul extraordinar pe care ea il contine.");
        db.insert(ALL_BOOKS_TABLE, null, cv);

        cv.put(COLUMN_BOOK_NAME, "12 reguli de viata");
        cv.put(COLUMN_BOOK_AUTHOR, "Jordan Peterson");
        cv.put(COLUMN_BOOK_PAGES, 424);
        cv.put(COLUMN_BOOK_GEN, "Psihologie");
        cv.put(COLUMN_BOOK_IMAGE_URL, "https://cdn4.libris.ro/img/pozeprod/59/1010/2F/1257217-WRHT.jpg");
        cv.put(COLUMN_BOOK_DESCRIPTION, "A durat mult pana am gasit titlul: " +
                "12 Reguli de viata: un antidot la haosul din jurul nostru. " +
                "Acesta sugereaza clar faptul ca oamenii au nevoie de principii ordonatoare, " +
                "pentru a evita instaurarea haosului. Avem nevoie de reguli, standarde, " +
                "valori — si luati singuri, dar si luati impreuna. Suntem niste animale de povara, " +
                "obisnuite sa traga la jug. Avem nevoie de o incarcatura care sa ne justifice existenta mizerabila. " +
                "Avem nevoie de rutina si de traditie. Aceasta inseamna ordine. Daca am trai cum se cuvine, " +
                "am fi capabili sa toleram povara constiintei de sine. Daca am trai cum se cuvine, " +
                "am reusi sa ne asumam propria fragilitate si mortalitate, fara sa ne simtim ca niste victime nevinovate, " +
                "fara sa cadem in starea de invidie sau sa ajungem, mai apoi, la dorinta de razbunare si distrugere. " +
                "Daca am trai cum se cuvine, nu ar mai fi nevoie sa apelam la securitatea oferita de totalitarisme, " +
                "pentru a ne proteja de recunoasterea propriei finitudini si ignorante. " +
                "Poate ca am reusi sa evitam cararile ce duc spre Iad — iar secolul XX ne\u001Ea aratat cam cat de " +
                "real poate fi Iadul - Jordan B. Peterson");
        db.insert(ALL_BOOKS_TABLE, null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ALL_BOOKS_TABLE);
        onCreate(db);
    }

}
