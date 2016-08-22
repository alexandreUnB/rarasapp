package com.rarasnet.rnp.desordens.search.models.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados {
    public static String getKEY_ID() {
        return KEY_ID;
    }

    public static void setKEY_ID(String KEY_ID) {
        BancoDeDados.KEY_ID = KEY_ID;
    }

    public static String getKEY_NOME() {
        return KEY_NOME;
    }

    public static void setKEY_NOME(String KEY_NOME) {
        BancoDeDados.KEY_NOME = KEY_NOME;
    }

    public static String getKEY_ORPHANUMBER() {
        return KEY_ORPHANUMBER;
    }

    public static void setKEY_ORPHANUMBER(String KEY_ORPHANUMBER) {
        BancoDeDados.KEY_ORPHANUMBER = KEY_ORPHANUMBER;
    }

    public static String getKEY_EXPERTLINK() {
        return KEY_EXPERTLINK;
    }

    public static void setKEY_EXPERTLINK(String KEY_EXPERTLINK) {
        BancoDeDados.KEY_EXPERTLINK = KEY_EXPERTLINK;
    }

    public static String getKEY_DESCRICAO() {
        return KEY_DESCRICAO;
    }

    public static void setKEY_DESCRICAO(String KEY_DESCRICAO) {
        BancoDeDados.KEY_DESCRICAO = KEY_DESCRICAO;
    }

    static String KEY_ID = "_id";
    static String KEY_NOME = "nome";
    static String KEY_ORPHANUMBER = "orphanumber";
    static String KEY_EXPERTLINK = "expertlink";
    static String KEY_DESCRICAO = "decricao";


    String NOME_BANCO = "dba";
    String NOME_TABELA = "dis";
    int VERSAO_BANCO = 8;
    String SQL_CREATE_TABLE = "create table " + NOME_TABELA + " " +
            "(" + KEY_ID + " integer primary key, "
            + KEY_NOME + " text not null, " +
            KEY_ORPHANUMBER + " text, " +
            KEY_EXPERTLINK + " text," +
            KEY_DESCRICAO + " text);";

    final Context context;
    MeuOpenHelper openHelper;
    SQLiteDatabase db;

    public BancoDeDados(Context ctx) {
        this.context = ctx;
        openHelper = new MeuOpenHelper(context);
    }

    private class MeuOpenHelper extends SQLiteOpenHelper {
        MeuOpenHelper(Context context) {
            super(context, NOME_BANCO, null, VERSAO_BANCO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(SQL_CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    public BancoDeDados abrir() throws SQLException {
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void fechar() {
        openHelper.close();
    }

    public long insereDisease(String id, String nome, String orphanumber, String expertlink, String decricao) {
        ContentValues campos = new ContentValues();
        campos.put(KEY_ID, id);
        campos.put(KEY_NOME, nome);
        campos.put(KEY_ORPHANUMBER, orphanumber);
        campos.put(KEY_EXPERTLINK, expertlink);
        campos.put(KEY_DESCRICAO, decricao);


        return db.insert(NOME_TABELA, null, campos);
    }

    public boolean apagaDisease(long id) {
        return db.delete(NOME_TABELA, KEY_ID + "=" + id, null) > 0;
    }

    public Cursor retornaTodosDiseases() {
        return db.query(NOME_TABELA, new String[]{
                        KEY_ID, KEY_NOME, KEY_ORPHANUMBER, KEY_EXPERTLINK, KEY_DESCRICAO},
                null, null, null, null, null, null);
    }

    public Cursor retornaDisease(long id) throws SQLException {
        Cursor mCursor = db.query(true, NOME_TABELA, new String[]{
                        KEY_ID, KEY_NOME, KEY_ORPHANUMBER, KEY_EXPERTLINK, KEY_DESCRICAO},
                KEY_ID + "=" + id,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean atualizaDisease(long id, String nome,
                                   String orphanumber, String expertlink, String descricao) {
        ContentValues args = new ContentValues();
        args.put(KEY_NOME, nome);
        args.put(KEY_ORPHANUMBER, orphanumber);
        args.put(KEY_EXPERTLINK, expertlink);
        args.put(KEY_DESCRICAO, descricao);

        return db.update(NOME_TABELA, args, KEY_ID + "=" + id, null) > 0;
    }
}
