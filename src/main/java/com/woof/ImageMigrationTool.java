//package com.woof;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.List;
//import java.util.UUID;
//
//@SpringBootApplication
//public class ImageMigrationTool implements CommandLineRunner {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public static void main(String[] args) {
//        SpringApplication.run(ImageMigrationTool.class, args);
//    }
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        String selectSql = "SELECT PROD_NO, PROD_PHOTO FROM product WHERE PROD_PHOTO IS NOT NULL AND PROD_PHOTO NOT LIKE '%.png'";
//        List<ProductPhoto> photos = jdbcTemplate.query(selectSql, (rs, rowNum) -> new ProductPhoto(
//                rs.getInt("PROD_NO"),
//                rs.getBytes("PROD_PHOTO")
//        ));
//
//        String directoryPath = "src/main/resources/static/productImage";
//        File directory = new File(directoryPath);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        photos.forEach(photo -> {
//            String fileName = UUID.randomUUID().toString() + ".png";
//            File imageFile = new File(directoryPath, fileName);
//            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
//                fos.write(photo.getPhoto());
//                // 更新數據庫中的圖片路徑
//                String updateSql = "UPDATE product SET PROD_PHOTO = ? WHERE PROD_NO = ?";
//                jdbcTemplate.update(updateSql, fileName, photo.getProdNo());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    static class ProductPhoto {
//        private final int prodNo;
//        private final byte[] photo;
//
//        public ProductPhoto(int prodNo, byte[] photo) {
//            this.prodNo = prodNo;
//            this.photo = photo;
//        }
//
//        public int getProdNo() {
//            return prodNo;
//        }
//
//        public byte[] getPhoto() {
//            return photo;
//        }
//    }
//}
