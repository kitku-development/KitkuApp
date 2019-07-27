package com.kitku.kitku;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

class z_BackendPreProcessing {

    // Notice:
    // Bertujuan untuk mempermudah proses data untuk backend
    // sebagian besar fungsi disini bertujuan mengubah data mentah menjadi data JSON
    // pengiriman data ke host harus selalu menggunakan AsyncTask
    //
    // AsyncTask tidak dapat dilakukan disini
    // AsyncTask hanya dapat dilakukan pada Activity
    // karena proses setelah AsyncTask selesai (onPostExecute()) hanya dapat dilakukan pada Activity

    //Deklarasi URL untuk diakses di berbagai activity
    static String
            URL_RegisterPelanggan   = "https://kitku.id/pelanggan/register",
            URL_LoginPelanggan      = "https://kitku.id/pelanggan/login",
            URL_DataPelanggan       = "https://kitku.id/pelanggan/data/",
            //URL_RegisterMitra       = "https://kitku.id/dummy/create/register_supplier.php",
            URL_ProdukPerKategori   = "https://kitku.id/produk/kategori/",
            URL_ProdukDetail        = "https://kitku.id/produk/detail/",
            URL_ProdukSupplier      = "https://kitku.id/produk/supplier/",
            URL_DetailPemesanan     = "https://kitku.id/invoice/detail/",
            URL_ListPemesananUser   = "https://kitku.id/invoice/pelanggan/",
            URL_InsertPemesanan     = "https://kitku.id/invoice/add";

    // convert JSON supaya bisa diakses
    // Example JSON
    // {"Products":[
    //      {"id":"BAR-1","nama":"barang 1","satuan":"1.00","harga":"1000","jumlah":"8","url":"https://"},
    //      {"id":"BAR-2","nama":"barang 2","satuan":"1.00","harga":"1000","jumlah":"6","url":"https://"}
    //  ]}
    String[][] bacaListProduk(String rawData) throws Exception {

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
    String[] bacaDetailProduk(String rawData) throws Exception {

        // Buat JSONObject dari String
        JSONObject jsonObject       = new JSONObject(rawData);
        //Log.d("a",jsonObject.toString());

        // Buat JSONArray dengan informasi dari JSONObject dengan informasi Products
        // Inisialisasi array untuk penyimpanan data
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
    String registerPelanggan(String nama, String email, String password,
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
    String loginPelanggan(String user, String pass) throws Exception{
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
    String kirimPemesanan(String user, String[] id_bar, int[] jumlah,
                          int[] harga, int ongkos, String pengiriman,
                          String catatan) throws  Exception{
        // Masukkan data sebagai JSONObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id_pelanggan", user);

        // Untuk id_barang, jumlah, harga dibuat dalam bentuk array
        StringBuilder arrayBarang = new StringBuilder("[");
        StringBuilder arrayJumlah = new StringBuilder("[");
        StringBuilder arrayHarga  = new StringBuilder("[");

        // Looping memasukkan data id_barang, jumlah, harga kedalam array
        for(int index = 0; index < id_bar.length; index++){
            /*if (index > 0) {
                arrayBarang.append(",");
                arrayJumlah.append(",");
                arrayHarga.append(",");
            }*/
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
        arrayHarga.append("]");

        // masukan array dan sisa data ke JSONObject
        jsonObject.put("id_barang",     arrayBarang);
        jsonObject.put("jumlah",        arrayJumlah);
        jsonObject.put("harga",         arrayHarga);
        jsonObject.put("ongkos",        ongkos);
        jsonObject.put("waktu_kirim",   pengiriman);
        jsonObject.put("catatan",       catatan);

        return jsonObject.toString();
    }
}
