## Dokumentasi Pengembangan

### Dibutuhkan
- OpenJDK 17
- Intellij IDEA
- Docker

### Sebelum pengembangan
- Silahkan nonaktifkan absensi-web pada ```docker/docker-compose.yaml``` dengan cara melakukan `comment` pada block `services.web`
- Eksekusi `./bootstrap` untuk menjalankan `postgres` dan `localstack` di `container docker`
- Periksa status dari `docker container`
- Perlu di ingat biasanya `postgres` gagal dijalankan pada saat pertama kali eksekusi, jika terjadi hal demikian silahkan eksekusi ulang `./bootsrap`.
- impor API collection di folder `postman` **hanya jika menggunakan postman*
- impor Environment di folder `postman` **hanya jika menggunakan postman*
- aktifkan environment yang sudah di impor

### Menggunakan postman
- InitData
- login admin (gunakan `email` dan `password` dari response `InitData`)
- salin `token` dan tempelkan di `postman environment`

### Module API

berisi:

- Model Database (Entity / Table)
- REST Controller
- Repository
- Service


### Folder structure
```
+---docker                                   <---- docker configuration 
|   \---.data                                <---- local docker volume mapping
|       +---localstack
|       \---postgresql
+---src
|   +---main
|   |   +---java
|   |   |   \---com
|   |   |       \---test
|   |   |           \---absensi
|   |   |               +---auth                 <---- Module API
|   |   |               +---config
|   |   |               |   +---db
|   |   |               |   +---jwt
|   |   |               |   \---security
|   |   |               +---department           <---- Module API
|   |   |               +---exceptions
|   |   |               +---jabatan              <---- Module API
|   |   |               +---models              
|   |   |               +---employee              <---- Module API
|   |   |               +---pendidikan           <---- Module API
|   |   |               +---company           <---- Module API
|   |   |               +---attendance             <---- Module API
|   |   |               |   \---models
|   |   |               +---status_absensi       <---- Module API
|   |   |               +---unit_kerja           <---- Module API
|   |   |               +---user                 <---- Module API
|   |   |               \---utils              
|   |   \---resources
|   \---test
|       \---java
|           \---com
|               \---test
|                   \---absensi

```

### DB Table
![DB Table](https://lh3.googleusercontent.com/pw/AIL4fc_dffwWvgFPTwCHIfb4i8XqthiFArbmjjm_6IcmwixIog5Mr9LEjXW8vmpYBXXhYCn4yeGol5hGxE-WL7_bQZzE-7vscfkLX8e0hlXTbAgc8GFYuab0drjzPsXlqvnf8_8o7RT4JJjQbu3gMcLTSHQP=w1148-h840-s-no)
