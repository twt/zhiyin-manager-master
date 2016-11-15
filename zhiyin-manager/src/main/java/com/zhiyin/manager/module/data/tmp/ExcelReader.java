/**
 * ClassName:ExcelReader.<a href="http://lib.csdn.net/base/17" class='replace_word' title="Java EE知识库" target='_blank' style='color:#df3434; font-weight:bold;'>Java</a>
 * Author: wenbin.ji
 * CreateTime: Jan 28, 2011 11:16:29 AM
 * Description:Excel数据读取工具类，POI实现，兼容Excel2003，及Excel2007
 **/
package com.zhiyin.manager.module.data.tmp;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyin.dbs.module.address.entity.CustomAddressLocation;
import com.zhiyin.dbs.module.address.entity.LonLat;
import com.zhiyin.dbs.module.address.factory.PolygonLocType;
import com.zhiyin.dbs.module.address.util.PolygonLocUtil;
import com.zhiyin.manager.module.address.module.Point;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelReader {
  Workbook wb = null;
  List<String[]> dataList = new ArrayList<String[]>(100);
  public ExcelReader(String path){
    try {
      InputStream inp = new FileInputStream(path);
      wb = WorkbookFactory.create(inp);      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }  
  
  /**
   * 取Excel所有数据，包含header
   * @return  List<String[]>
   */
 public List<String[]> getAllData(int sheetIndex){
    int columnNum = 0;
    Sheet sheet = wb.getSheetAt(sheetIndex);
    if(sheet.getRow(0)!=null){
        columnNum = sheet.getRow(0).getLastCellNum()-sheet.getRow(0).getFirstCellNum();
    }
     columnNum = 100;
    if(columnNum>0){
      for(Row row:sheet){ 
          String[] singleRow = new String[columnNum];
          int n = 0;
          for(int i=0;i<columnNum;i++){
             Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
             switch(cell.getCellType()){
               case Cell.CELL_TYPE_BLANK:
                 singleRow[n] = "";
                 break;
               case Cell.CELL_TYPE_BOOLEAN:
                 singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
                 break;
                //数值
               case Cell.CELL_TYPE_NUMERIC:               
                 if(DateUtil.isCellDateFormatted(cell)){
                   singleRow[n] = String.valueOf(cell.getDateCellValue());
                 }else{ 
                   cell.setCellType(Cell.CELL_TYPE_STRING);
                   String temp = cell.getStringCellValue();
                   //判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                   if(temp.indexOf(".")>-1){
                     singleRow[n] = String.valueOf(new Double(temp)).trim();
                   }else{
                     singleRow[n] = temp.trim();
                   }
                 }
                 break;
               case Cell.CELL_TYPE_STRING:
                 singleRow[n] = cell.getStringCellValue().trim();
                 break;
               case Cell.CELL_TYPE_ERROR:
                 singleRow[n] = "";
                 break;  
               case Cell.CELL_TYPE_FORMULA:
                 cell.setCellType(Cell.CELL_TYPE_STRING);
                 singleRow[n] = cell.getStringCellValue();
                 if(singleRow[n]!=null){
                   singleRow[n] = singleRow[n].replaceAll("#N/A","").trim();
                 }
                 break;  
               default:
                 singleRow[n] = "";
                 break;
             }
             n++;
          } 
//          if("".equals(singleRow[0])){continue;}//如果第一行为空，跳过
          dataList.add(singleRow);
      }
    }
    return dataList;
  }  
  /**
   * 返回Excel最大行index值，实际行数要加1
   * @return
   */
  public int getRowNum(int sheetIndex){
    Sheet sheet = wb.getSheetAt(sheetIndex);
    return sheet.getLastRowNum();
  }
  
  /**
   * 返回数据的列数
   * @return 
   */
  public int getColumnNum(int sheetIndex){
    Sheet sheet = wb.getSheetAt(sheetIndex);
    Row row = sheet.getRow(0);
    if(row!=null&&row.getLastCellNum()>0){
       return row.getLastCellNum();
    }
    return 0;
  }
  
  /**
   * 获取某一行数据
   * @param rowIndex 计数从0开始，rowIndex为0代表header行
   * @return
   */
    public String[] getRowData(int sheetIndex,int rowIndex){
      String[] dataArray = null;
      if(rowIndex>this.getColumnNum(sheetIndex)){
        return dataArray;
      }else{
        dataArray = new String[this.getColumnNum(sheetIndex)];
        return this.dataList.get(rowIndex);
      }
      
    }
  
  /**
   * 获取某一列数据
   * @param colIndex
   * @return
   */
  public String[] getColumnData(int sheetIndex,int colIndex){
    String[] dataArray = null;
    if(colIndex>this.getColumnNum(sheetIndex)){
      return dataArray;
    }else{   
      if(this.dataList!=null&&this.dataList.size()>0){
        dataArray = new String[this.getRowNum(sheetIndex)+1];
        int index = 0;
        for(String[] rowData:dataList){
          if(rowData!=null){
             dataArray[index] = rowData[colIndex];
             index++;
          }
        }
      }
    }
    return dataArray;
    
  }

    public static Map<String,Object> getAddr() throws IOException {

        String path ="C:\\Users\\hg\\Desktop\\data\\坐标更新.xlsx";
        path ="C:\\Users\\hg\\Desktop\\20161108更新\\20161108坐标更新.xlsx";
        ExcelReader reader = new ExcelReader(path);
        List<String[]> data = reader.getAllData(0);

//        System.out.println( JSON.toJSONString( data ) );

        Map<String,AddrInfo> parMap = Maps.newHashMap();
        Map<String,ArrayList<AddrInfo>> parSonMap = Maps.newHashMap();
        for( int i=0;i<data.size(); i++){
            System.out.println(JSON.toJSONString(data.get(i)));
            String[] tmp = data.get(i);
            String parAddrName = tmp[0];
            String addrName = tmp[1];

            if( Strings.isNullOrEmpty(parAddrName) && Strings.isNullOrEmpty(addrName) ){
                continue;
            }

            if(!parMap.containsKey( parAddrName )){
                parMap.put(parAddrName , addr5(parAddrName) );
            }
            if(!parSonMap.containsKey( parAddrName )){
                parSonMap.put( parAddrName, new ArrayList<AddrInfo>());
            }

            AddrInfo son = addr6(addrName);

            String[] addrLocShapeInfo = tmp[3].split(",");
            Integer addrNum = Integer.valueOf(addrLocShapeInfo[0]);// 热点区域数量
            Integer shapeTypeIndex = 1 ;//
            Integer pointNumIndex = 1 + addrNum;
            Integer startIndex = 4 ; // 坐标开始列数
            for(int locIndex=0; locIndex< addrNum ; locIndex++){

                Integer shapeType = Integer.valueOf(addrLocShapeInfo[shapeTypeIndex]);
                Integer pointNum = Integer.valueOf(addrLocShapeInfo[pointNumIndex] );
                List<Point> points = Lists.newArrayList();

                points = addrPolygon(tmp,startIndex,pointNum);

                CustomAddressLocation location = new CustomAddressLocation();
                location.setRectangleCoord(2);

                getPolyCenter(points,location,shapeType);
                son.setOverlayDegree(Integer.valueOf(tmp[2]));
                son.setCenterIsset(1);
                son.setCenterLongitude( (location.getRectangleX1() + location.getRectangleX2()) /2 ) ;
                son.setCenterLatitude( (location.getRectangleY1() + location.getRectangleY2()) /2 );
                son.getLocList().add(location);

//                System.out.println( JSON.toJSONString(location));
                startIndex += pointNum;
                shapeTypeIndex ++;
                pointNumIndex ++;

                log.info(JSON.toJSONString(location));
//                shapeIndex +=addrNum;
            }

            log.info(JSON.toJSONString(son));


//            for(int locIndex=2;locIndex<data.size();locIndex=locIndex+2){
//                if(Strings.isNullOrEmpty(tmp[locIndex])){
//                    break;
//                }
//                CustomAddressLocation location = new CustomAddressLocation();
//                location.setRectangleCoord(2);
//                conv(tmp[locIndex],tmp[locIndex+1],location);
//
//                son.setCenterIsset(1);
//                son.setCenterLongitude( (location.getRectangleX1() + location.getRectangleX2()) /2 ) ;
//                son.setCenterLatitude( (location.getRectangleY1() + location.getRectangleY2()) /2 );
//
//                son.getLocList().add(location);
//            }
            parSonMap.get(tmp[0]).add(son);
        }

        for(Map.Entry<String,ArrayList<AddrInfo>> entry : parSonMap.entrySet()){
            System.out.println( entry.getKey()  );
            for(AddrInfo info : entry.getValue()){
                String ll = "";
                for(CustomAddressLocation c : info.getLocList()){
                    ll += c.getLocType()+","+ c.getRectangleX1() + ","+ c.getRectangleY1() + ";"+c.getRectangleX2()+","+c.getRectangleY2()+";";
                }
                System.out.println( "\t" +info.getName() + "\t"+info.getLocList().size() + "\t" + ll);
                log.info(JSON.toJSONString(info));
            }
        }

        Map<String,Object> retMap = Maps.newHashMap();

        retMap.put("parSonMap",parSonMap);
        retMap.put("parMap",parMap );

        return retMap;
    }

    public static void main(String[] args) throws IOException {
        ExcelReader.getAddr();
    }

    public static void conv(String info1,String info2, CustomAddressLocation location){
        String[] ll = info1.split(",");
        String[] ll2 = info2.split(",");

        List<Double> lonList = Lists.newArrayList();
        List<Double> latList = Lists.newArrayList();

        lonList.add(Double.valueOf(ll[0]));
        latList.add(Double.valueOf(ll[1]));
        lonList.add(Double.valueOf(ll2[0]));
        latList.add(Double.valueOf(ll2[1]));

        Collections.sort(lonList);
        Collections.sort(latList);
        location.setRectangleX1( lonList.get(0) );
        location.setRectangleY1( latList.get(0) );
        location.setRectangleX2( lonList.get( lonList.size()-1 ) );
        location.setRectangleY2( latList.get( latList.size()-1 ) );
    }


    public static AddrInfo addr5(String name){
        AddrInfo ca = new AddrInfo();
        ca.setName(name.trim());
        ca.setParentId(101L);
        ca.setDegree(5);
        ca.setCenterIsset(0);
        return ca;
    }

    public static AddrInfo addr6(String name){
        AddrInfo ca = new AddrInfo();
        ca.setName(name.trim());
        ca.setDegree(6);
        ca.setCenterIsset(1);
        ca.setCenterCoord(2);
        return ca;
    }

    public static List<Point> addrPolygon(String[] data, Integer start, Integer num){

        List<Point> points = Lists.newArrayList();

        log.info("{} {}",start,num);
        for(int i= start;i<num+start;i++){

            String[] info = data[i].split(",");
//            log.info("{} {}",i,data[i]);
            Point p = new Point();
            p.setLon(Double.valueOf(info[0]));
            p.setLat( Double.valueOf(info[1]));
            points.add(p);

        }

        return points;
    }

    // 获取多边形的中心点
    public static void getPolyCenter(List<Point> points, CustomAddressLocation location,Integer type){
        List<Double> lonList = Lists.newArrayList();
        List<Double> latList = Lists.newArrayList();

        List<LonLat> lonLats = Lists.newArrayList();
        for( Point tmp : points){
            lonList.add(tmp.getLon());
            latList.add(tmp.getLat());
            lonLats.add(new LonLat(tmp.getLon(),tmp.getLat()));
        }
        Collections.sort(lonList);
        Collections.sort(latList);

        location.setRectangleX1( lonList.get(0) );
        location.setRectangleY1( latList.get(0) );
        location.setRectangleX2( lonList.get( lonList.size()-1 ) );
        location.setRectangleY2( latList.get( latList.size()-1 ) );

        location.setRectangleCoord(2);
        location.setCenterpointCoord(2);

        if(type==1){
            location.setLocType(PolygonLocType.RecPoly.getType());
            location.setPolygonPoints(PolygonLocUtil.polygonPoints(lonLats));
            location.setPolygonCoord(2);
        }else{
            location.setLocType(PolygonLocType.Rectangle.getType());
        }

    }
}
