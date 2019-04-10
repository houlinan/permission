package cn.hgxsp.controller;

import cn.hgxsp.dto.POIDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/10
 * Time : 17:01
 */
@Controller
@Slf4j
public class ExTVTitleController {

    @RequestMapping("/tv")
    public String ExportSensitiveWords(HttpServletResponse response ){
        log.info("进入了tv方法");
        ExportSensitiveWordsExcel(response);
        return "chenggong" ;
    }



    //提取标题功能提excel

    public String ExportSensitiveWordsExcel(HttpServletResponse response )  {

        try {
            exportExcelForTvTitle("1", response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * Description: TV导出标题<BR>
     * @author hou.linan
     * @throws UnsupportedEncodingException
     * @date 2019年4月10日
     */
    private static String exportExcelForTvTitle(String str ,HttpServletResponse response) throws UnsupportedEncodingException{

        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet();
        //设置第三列长度
        sheet.setColumnWidth(1,20 * 256);
        sheet.setColumnWidth(2,80 * 256);
        sheet.setColumnWidth(3,20 * 256);
        sheet.setColumnWidth(4,17 * 256);
        sheet.setColumnWidth(5,20 * 256);
        sheet.autoSizeColumn((short) 10);



        /**
         * 设置第一行
         * */
        //设置标题
        // 第三步，在sheet中添加表头第0行
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short)900);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("2019-03-06 test 版本");
        cell.setCellStyle(createNewStye(wb, 20 , "新宋体", true , false ));


        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,2 ));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3,5 ));

        HSSFCell cell0_3 = row.createCell(3);
        cell0_3.setCellValue("2019年03月06日 星期三");
        HSSFCellStyle dateStyle = createNewStye(wb, 15, "新宋体", false, false);
        dateStyle.setBorderBottom(BorderStyle.THIN);
        cell0_3.setCellStyle(dateStyle);



        /**
         * 设置第二行
         * */
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short)500);

        HSSFCellStyle cell1Sytle = createNewStye(wb, 12, "宋体", true, true);

        //0
        HSSFCell cell1_0 = row1.createCell(0);
        cell1_0.setCellValue("序号");
        cell1_0.setCellStyle(cell1Sytle);

        //1
        HSSFCell cell1_1 = row1.createCell(1);
        cell1_1.setCellValue("形式");
        cell1_1.setCellStyle(cell1Sytle);

        //2
        HSSFCell cell1_2 = row1.createCell(2);
        cell1_2.setCellValue("标题");
        cell1_2.setCellStyle(cell1Sytle);
        //2
        HSSFCell cell1_3 = row1.createCell(3);
        cell1_3.setCellValue("时长");
        cell1_3.setCellStyle(cell1Sytle);
        //2
        HSSFCell cell1_4 = row1.createCell(4);
        cell1_4.setCellValue("记者");
        cell1_4.setCellStyle(cell1Sytle);
        //2
        HSSFCell cell1_5 = row1.createCell(5);
        cell1_5.setCellValue("通讯员");
        cell1_5.setCellStyle(cell1Sytle);

        int lastRowNum = 1 ;
        //设置第三行正文
        List<POIDto> allDto = POIDto.getAllDto();
        for(int a = 0 ;a < allDto.size() ; a++ ){
            int b = a ;

            HSSFRow rowX = sheet.createRow(b + 2 );
            row1.setHeight((short)600);

            POIDto currDto = allDto.get(a);
            //0
            HSSFCell cellx_0 = rowX.createCell(0);
            cellx_0.setCellValue(b + 1 );
            cellx_0.setCellStyle(cell1Sytle);

            //1
            HSSFCell cellx_1 = rowX.createCell(1);
            cellx_1.setCellValue(currDto.getXingshi());
            cellx_1.setCellStyle(cell1Sytle);

            //2
            HSSFCell cellx_2 = rowX.createCell(2);
            cellx_2.setCellValue(currDto.getBiaoti());
            cellx_2.setCellStyle(cell1Sytle);
            //2
            HSSFCell cellx_3 = rowX.createCell(3);
            cellx_3.setCellValue(currDto.getShichang());
            cellx_3.setCellStyle(cell1Sytle);
            //2
            HSSFCell cellx_4 = rowX.createCell(4);
            cellx_4.setCellValue(currDto.getJizhe());
            cellx_4.setCellStyle(cell1Sytle);
            //2
            HSSFCell cellx_5 = rowX.createCell(5);
            cellx_5.setCellValue(currDto.getTongxunyuan());
            cellx_5.setCellStyle(cell1Sytle);

            lastRowNum++ ;
            System.out.println(lastRowNum + ";" +allDto.size() );
        }
        lastRowNum++;
        String yuboshichang = "00:00：18"; // TODO

        HSSFRow rowLast = sheet.createRow(lastRowNum);
        rowLast.setHeight((short)1100);
        HSSFCell cellLast = rowLast.createCell(0);
        cellLast.setCellValue("预播时长：" + yuboshichang +"  实播时长：___分____秒  编辑：贺佳  值班主任：");
        cellLast.setCellStyle(createNewStye(wb, 18 , "新宋体", true , false ));

        sheet.addMergedRegion(new CellRangeAddress(lastRowNum, lastRowNum, 0,5 ));


        //让列宽随着导出的列长自动适应
        for (int colNum = 0; colNum < 10; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
        }


        // 第六步，将文件存到指定位置
        response.setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode("提取口播", "utf-8") + ".xls");
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            wb.write(out);

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "";
    }

    private static HSSFCellStyle createNewStye(HSSFWorkbook wb , int fontSize , String fontName , Boolean setBold  ,boolean allBorder ){
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //设置第一行的格式
        // 1.生成字体对象
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) fontSize);
        font.setFontName(fontName);
        font.setBold(setBold);

        // 2.生成样式对象
        style.setFont(font); // 调用字体样式对象
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置上下居中

        if(allBorder){
            style.setBorderBottom(BorderStyle.THIN);//下边框
            style.setBorderLeft(BorderStyle.THIN);//左边框
            style.setBorderRight(BorderStyle.THIN);//右边框
            style.setBorderTop(BorderStyle.THIN);//上边框
        }

        return style ;
    }


}
