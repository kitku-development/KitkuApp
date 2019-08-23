package com.kitku.kitku.BackgroundProcess;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class z_BackendPreProcessing {

    // Notice:
    // Bertujuan untuk mempermudah proses data untuk backend
    // sebagian besar fungsi disini bertujuan mengubah data mentah menjadi data JSON
    // pengiriman data ke host harus selalu menggunakan AsyncTask
    //
    // AsyncTask tidak dapat dilakukan disini
    // AsyncTask hanya dapat dilakukan pada Activity
    // karena proses setelah AsyncTask selesai (onPostExecute()) hanya dapat dilakukan pada Activity

    //Deklarasi URL untuk diakses di berbagai activity
    public static String
            URL_UserRegister        = "https://kitku.id/pelanggan/register",
            URL_UserLogin           = "https://kitku.id/pelanggan/login",
            URL_UserData            = "https://kitku.id/pelanggan/data/",
            URL_SupplierLogin       = "https://kitku.id/supplier/login",
            URL_SupplierData        = "https://kitku.id/supplier/data/",
            URL_ProductCategory     = "https://kitku.id/produk/kategori/",
            URL_ProductDetail       = "https://kitku.id/produk/detail/",
            URL_ProductSupplier     = "https://kitku.id/produk/supplier/",
            URL_InvoiceDetail       = "https://kitku.id/invoice/detail/",
            URL_InvoiceListUser     = "https://kitku.id/invoice/pelanggan/",
            URL_InvoiceInsert       = "https://kitku.id/invoice/add";

    // convert JSON supaya bisa diakses
    // Example JSON
    // {"Products":[
    //      {"id":"BAR-1","nama":"barang 1","satuan":"1.00","harga":"1000","jumlah":"8","url":"https://"},
    //      {"id":"BAR-2","nama":"barang 2","satuan":"1.00","harga":"1000","jumlah":"6","url":"https://"}
    //  ]}
    public String[][] readProductList(String rawData) throws Exception {

        // String bisa diconvert menjadi JSONObject atau JSONArray
        // Jika menggunakan JSONObject maka pengaksesannya dengan pemeriksaan string index
        // misal { "id_barang" : "BAR-1" }
        // maka untuk aksesnya digunakan getString("id_barang")
        // getString bisa diganti dengan getInt, getLong, dan get
        //
        // Namun jika menggunakan JSONArray maka pengaksesannya dengan index array
        // misal { "id_barang" : "BAR-1" }
        // maka untuk aksesnya digunakan  get(0)
        // get() bisa diganti dengan getDouble, getString, getInt, getLong
        //Log.d("b4",rawData);

        // Buat JSONObject dari String
        JSONObject jsonObject       = new JSONObject(rawData);
        //Log.d("a",jsonObject.toString());

        // Buat JSONArray dengan informasi dari JSONObject dengan informasi Products
        // Inisialisasi array untuk penyimpanan data
        JSONArray jsonArray         = jsonObject.getJSONArray("Products");
        String[] id_barang          = new String[jsonArray.length()];
        String[] nama_barang        = new String[jsonArray.length()];
        String[] satuan             = new String[jsonArray.length()];
        String[] harga              = new String[jsonArray.length()];
        String[] jumlah_stok        = new String[jsonArray.length()];
        String[] url_gambar         = new String[jsonArray.length()];

        // looping akses JSONArray
        // tiap baris array dijadikan JSONObject karena pada 1 baris array terdapat banyak data
        // masukkan data ke dalam array dengan getString("nama informasi")
        for (int index = 0; index < jsonArray.length(); index++) {
            JSONObject baris        = jsonArray.getJSONObject(index);
            id_barang[index]        = baris.getString("id");
            nama_barang[index]      = baris.getString("nama");
            satuan[index]           = baris.getString("satuan");
            harga[index]            = baris.getString("harga");
            jumlah_stok[index]      = baris.getString("jumlah");
            url_gambar[index]       = baris.getString("url");
        }

        // Kirim informasi array tersebut ke dalam array baru
        return new String[][]{
                id_barang,
                nama_barang,
                satuan,
                harga,
                jumlah_stok,
                url_gambar
        };
    }

    // convert JSON supaya bisa diakses
    // Example JSON
    // {"Products":[
    //      {"id":"BAR-1","nama":"barang 1","desc":"blabla","satuan":"0.00","jumlah":"8","review":"0.00","url":"https://"}
    //  ]}
    public String[] readProductDetail(String rawData) throws Exception {

        // Buat JSONObject dari String
        JSONObject jsonObject       = new JSONObject(rawData);
        //Log.d("a",jsonObject.toString());

        // Buat JSONArray dengan informasi dari JSONObject dengan informasi Products
        JSONArray jsonArray     = jsonObject.getJSONArray("Products");
        JSONObject baris        = jsonArray.getJSONObject(0);
        String nama_barang      = baris.getString("nama");
        String desc             = baris.getString("desc");
        String satuan           = baris.getString("satuan");
        String harga            = baris.getString("harga");
        String jumlah_stok      = baris.getString("jumlah");
        String review           = baris.getString("review");
        String url_gambar       = baris.getString("url");

        // Kirim informasi array tersebut ke dalam array baru
        return new String[]{
                nama_barang,
                desc,
                satuan,
                harga,
                jumlah_stok,
                review,
                url_gambar
        };
    }

    // Buat JSON menjadi seperti berikut
    // {
    //    "password"  : "",
    //    "nama"      : "",
    //    "alamat"    : "",
    //    "kontak"    : "",
    //    "email"     : ""
    // }
    public String registerUser(String nama, String email, String password,
                        String alamat, String kontak) throws Exception {
        // buat JSONObject baru dan masukkan data kedalam JSONObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password", password);
        jsonObject.put("nama", nama);
        jsonObject.put("alamat", alamat);
        jsonObject.put("kontak", kontak);
        jsonObject.put("email", email);

        /*Log.d("json", json); String response = new serverAccess().accessHost(url, json);*/
        return jsonObject.toString();
    }


    // Buat JSON menjadi seperti
    // {
    //    "email"     : "",
    //    "password"  : ""
    // }
    public String loginUserAndMitra(String user, String pass) throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", user);
        jsonObject.put("password", pass);

        return jsonObject.toString();
    }

    // Buat JSON menjadi seperti
    // {
    //    "id_pelanggan"    : "",
    //    "id_barang"       : ["",""],
    //    "jumlah"          : ["",""],
    //    "harga"           : ["",""],
    //    "ongkos"          : "",
    //    "waktu_kirim"     : "",
    //    "catatan"         : ""
    // }
    String sendOrderList(String user, String[] id_bar, int[] jumlah,
                         int[] harga, int ongkos, String pengiriman,
                         String catatan) throws  Exception {
        // Masukkan data sebagai JSONObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id_pelanggan", user);

        // Untuk id_barang, jumlah, harga dibuat dalam bentuk array
        /*StringBuilder arrayBarang = new StringBuilder("[");
        StringBuilder arrayJumlah = new StringBuilder("[");
        StringBuilder arrayHarga  = new StringBuilder("[");

        // Looping memasukkan data id_barang, jumlah, harga kedalam array
        for(int index = 0; index < id_bar.length; index++){
            /*if (index > 0) {
                arrayBarang.append(",");
                arrayJumlah.append(",");
                arrayHarga.append(",");
            }
            // 'id_bar'
            //arrayBarang.append("'");
            arrayBarang.append(id_bar[index]);
            //arrayBarang.append("'");

            // 'jumlah'
            //arrayJumlah.append("'");
            arrayJumlah.append(jumlah[index]);
            //arrayJumlah.append("'");

            // 'harga'
            //arrayHarga.append("'");
            arrayHarga.append(harga[index]);
            //arrayHarga.append("'");
        }
        // tutup array
        arrayBarang.append("]");
        arrayJumlah.append("]");
        arrayHarga.append("]");*/

        // masukan array dan sisa data ke JSONObject
        //jsonObject.put("id_barang",     arrayBarang);
        //jsonObject.put("jumlah",        arrayJumlah);
        //jsonObject.put("harga",         arrayHarga);
        jsonObject.put("ongkos",        ongkos);
        jsonObject.put("waktu_kirim",   pengiriman);
        jsonObject.put("catatan",       catatan);

        return jsonObject.toString();
    }

    // convert JSON supaya bisa diakses
    // Example JSON
    // {"Products":[
    //      {"nama_pelanggan":"tester","alamat_pelanggan":"Jl. Tester No. 0","kontak_pelanggan":"0000","waktu_terdaftar":"2019-07-02 11:34:13"}
    //  ]}
    public String[] readUserData(String rawData) throws Exception {

        // Buat JSONObject dari String
        JSONObject jsonObject       = new JSONObject(rawData);
        //Log.d("a",jsonObject.toString());

        // Buat JSONArray dengan informasi dari JSONObject dengan informasi Products
        JSONArray jsonArray     = jsonObject.getJSONArray("Pelanggans");
        JSONObject baris        = jsonArray.getJSONObject(0);
        String nama             = baris.getString("nama_pelanggan");
        String alamat           = baris.getString("alamat_pelanggan");
        String kontak           = baris.getString("kontak_pelanggan");
        String waktu            = baris.getString("waktu_terdaftar");

        // Kirim informasi array tersebut ke dalam array baru
        return new String[]{
                nama,
                alamat,
                kontak,
                waktu
        };
    }

    // Buat JSON menjadi seperti
    // {
    //    "email"     : "",
    //    "password"  : ""
    // }
    /*String loginMitra(String user, String pass) throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", user);
        jsonObject.put("password", pass);

        return jsonObject.toString();
    }
    NOTICE : procedure above has been replaced with loginUserAndMitra */

    // convert JSON supaya bisa diakses
    // Example JSON
    // {"Products":[
    //      {"nama_pelanggan":"tester","alamat_pelanggan":"Jl. Tester No. 0","kontak_pelanggan":"0000","waktu_terdaftar":"2019-07-02 11:34:13"}
    //  ]}
    public String[] readSupplierData(String rawData) throws Exception {

        // Buat JSONObject dari String
        JSONObject jsonObject       = new JSONObject(rawData);
        //Log.d("a",jsonObject.toString());

        // Buat JSONArray dengan informasi dari JSONObject dengan informasi Products
        JSONArray jsonArray     = jsonObject.getJSONArray("Suppliers");
        JSONObject baris        = jsonArray.getJSONObject(0);
        String nama             = baris.getString("nama_supplier");
        String alamat           = baris.getString("alamat_supplier");
        String kontak           = baris.getString("kontak_supplier");
        String waktu            = baris.getString("waktu_terdaftar");

        // Kirim informasi array tersebut ke dalam array baru
        return new String[]{
                nama,
                alamat,
                kontak,
                waktu
        };
    }

    public void addItemToCart(String id, String count, String price, Context context, String name,
                              String piece, String pack) throws Exception {
        // reinitiate price
        price = price.replace("Rp. ","");

        // get data from SharedPreference
        SharedPreferences userData = PreferenceManager.getDefaultSharedPreferences(
                Objects.requireNonNull(context));
        String jsonCart = "{}";
        if (userData.contains("Cart"))
            jsonCart = userData.getString("Cart",null);

        // Parse the data from SharedPreference (example JSON refer to sendOrderList above)
        // just get id_barang, jumlah, harga
        List<String> id_barang, jumlah, harga, nama, satuan, pak;
        assert jsonCart != null;
        JSONObject jsonObject  = new JSONObject(jsonCart);

        // if data exist -> read data, else -> create new
        if (jsonObject.has("id_barang")) {
            id_barang   = ItemListDeserialize(jsonObject.getJSONArray("id_barang"));
            jumlah      = ItemListDeserialize(jsonObject.getJSONArray("jumlah"));
            harga       = ItemListDeserialize(jsonObject.getJSONArray("harga"));
            nama        = ItemListDeserialize(jsonObject.getJSONArray("nama"));
            satuan      = ItemListDeserialize(jsonObject.getJSONArray("satuan"));
            pak         = ItemListDeserialize(jsonObject.getJSONArray("pack"));
        }
        else {
            id_barang   = new ArrayList<>();
            jumlah      = new ArrayList<>();
            harga       = new ArrayList<>();
            nama        = new ArrayList<>();
            satuan      = new ArrayList<>();
            pak         = new ArrayList<>();
        }

        boolean found = false;
        // compare data with the new one, if exist -> replace, if not -> add
        if (id_barang.size() > 0)
        {
            for (int index = 0; index < id_barang.size(); index++) {
                if (id_barang.get(index).equals(id)) {
                    if (!count.equals("0"))
                        jumlah.set(index,count);
                    else {
                        id_barang.remove(index);
                        jumlah.remove(index);
                        harga.remove(index);
                        nama.remove(index);
                        satuan.remove(index);
                        pak.remove(index);
                    }
                    found = true;
                }
            }
        }

        if (!found) {
            id_barang.add(id);
            jumlah.add(count);
            harga.add(price);
            nama.add(name);
            satuan.add(piece);
            pak.add(pack);
        }

        // Add data into new JSONObject
        jsonObject = new JSONObject();
        jsonObject.put("id_barang",new JSONArray(id_barang));
        jsonObject.put("jumlah", new JSONArray(jumlah));
        jsonObject.put("harga",new JSONArray(harga));
        jsonObject.put("nama", new JSONArray(nama));
        jsonObject.put("satuan", new JSONArray(satuan));
        jsonObject.put("pack", new JSONArray(pak));

        // Convert JSONObject into string
        jsonCart = jsonObject.toString();

        // edit shared preferences
        SharedPreferences.Editor userDataEdit = userData.edit();
        if (userData.contains("Cart")) userDataEdit.remove("Cart");
        userDataEdit.putString("Cart", jsonCart);
        userDataEdit.apply();
    }

    public List<String> ItemListDeserialize(JSONArray raw) throws Exception{
        List<String> newone = new ArrayList<>();
        for (int index = 0; index < raw.length(); index++) {
            newone.add(index,raw.getString(index));
        }
        //Log.d("des", newone.toString());
        return newone;
    }

    /*private String ItemListSerialize(String[] raw) {
        // Start with [
        StringBuilder array = new StringBuilder("[");

        // Loop to input data
        for(int index = 0; index < raw.length; index++){
            if (index > 0)
                array.append(",");
            array.append("{").append(raw[index]).append("}");
        }
        // Close array with ]
        array.append("]");

        return array.toString();
    }*/
}
