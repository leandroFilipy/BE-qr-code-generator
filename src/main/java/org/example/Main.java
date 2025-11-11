package org.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {


    public static void generateQRCodeImage(String data, String path, int width, int height)
            throws WriterException, IOException {


        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                data,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
        );

        Path filePath = FileSystems.getDefault().getPath(path);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);

        System.out.println("✅ QR Code gerado em: " + path);
    }

    public static void main(String[] args) {

        String filePath = "qrcode_gerado.png";
        int qrCodeWidth = 300;
        int qrCodeHeight = 300;

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("----------------------------------------");
            System.out.println("🚀 Gerador de QR Code em Java (ZXing)");
            System.out.println("----------------------------------------");

            System.out.print(" Digite o texto ou URL para o QR Code: ");
            String dataToEncode = scanner.nextLine();

            if (dataToEncode == null || dataToEncode.trim().isEmpty()) {
                System.out.println("❌ Erro: Nenhum dado foi fornecido. Encerrando.");
                return;
            }

            generateQRCodeImage(dataToEncode, filePath, qrCodeWidth, qrCodeHeight);

            System.out.println("\n----------------------------------------");
            System.out.println("✨ Concluído. O arquivo foi criado na pasta do projeto.");
            System.out.println("----------------------------------------");

        } catch (WriterException e) {
            System.err.println("Erro na codificação do QR Code. Verifique o dado fornecido.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro de I/O ao salvar o arquivo.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}