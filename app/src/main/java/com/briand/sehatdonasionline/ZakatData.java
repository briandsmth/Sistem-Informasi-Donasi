package com.briand.sehatdonasionline;

import java.util.ArrayList;

public class ZakatData {

    private static String[] zakatNames = {
            "Zakat Simpanan",
            "Zakat Penghasilan",
            "Zakat Emas dan Perak",
            "Zakat Perdagangan",
            "Zakat Fitrah"
    };

    private static String[] zakatDetail = {
            "'...dan orang-orang yang menyimpan emas dan perak (termasuk tabungan dan deposito) dan tidak menafkahkannya pada jalan Allah, Maka beritahukannlah kepada mereka,(Bahwa mereka akan mendapat siksa yang pedih).' (QS. At-Taubah(9): 34) ",
            "Zakat penghasilan adalah zakat yang di keluarkan dari penghasilan ketika telah mencapai nishab",
            "Hadits Nabi SAW 'Tidak ada seorangpun yang mempunyai emas dan perak yang dia tidak berikan zakatnya, melainkan pada hari kiamat dijadikan hartanya itu beberapa keping api neraka dan di setrikakan pada punggung dan jidatnya'.(HR. Muslim), dan ijma ulama.",
            "'Rasulullah SAW memerintahkan kepada kami agar mengeluarkan zakat dari semua yang kami persiapkan untuk berdagang'",
            "Zakat fitrah ialah zakat yang dikeluarkan bertepatan dengan momen bulan Ramadhan dan juga hari Raya Idul Fitri, yang diwajibkan kepada setiap diri orang islam baik anak kecil atau dewasa, lelaki atau wanita, miskin atau kaya, merdeka atau hamba sahaya."
    };

    static ArrayList<Zakat> getListData() {
        Zakat zakat;
        ArrayList<com.briand.sehatdonasionline.Zakat> List = new ArrayList<>();
        for (int posisi = 0; posisi < zakatNames.length; posisi++){
            zakat = new Zakat();
            zakat.setZakat(zakatNames[posisi]);
            zakat.setDetail(zakatDetail[posisi]);
            List.add(zakat);
        }
        return List;
    }
}
