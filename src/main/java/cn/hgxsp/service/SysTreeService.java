package cn.hgxsp.service;

import cn.hgxsp.dao.SysDeptMapper;
import cn.hgxsp.dto.DeptLevelDto;
import cn.hgxsp.model.SysDept;
import cn.hgxsp.util.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * DESC：计算组织的树形结构类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 15:12
 */
@Service
public class SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper ;


    public List<DeptLevelDto> deptTree(){
        //获取所有的部门列表
        List<SysDept> allDept = sysDeptMapper.getAllDept();
        //将数据做成deptDTOList
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for(SysDept sysDept :allDept){
            DeptLevelDto deptLevelDto = DeptLevelDto.adapt(sysDept);
            dtoList.add(deptLevelDto) ;
        }
        //将dtoList做成树形结构

        return deptListToTree(dtoList) ;
    }

    public List<DeptLevelDto> deptListToTree( List<DeptLevelDto> deptLevellist){

        if(CollectionUtils.isEmpty(deptLevellist)) return Lists.newArrayList() ;

        //将数据组装起来
        //定一个一个特殊的树形接口，  Multimap<deptDtpLevel, List<dept>>
        Multimap<String ,DeptLevelDto > levelDeptMap = ArrayListMultimap.create() ;
        List<DeptLevelDto> rootDeptList = Lists.newArrayList() ;

        //将所有数据装入Multimap中，
        for(DeptLevelDto deptLevelDto : deptLevellist){
            levelDeptMap.put(deptLevelDto.getLevel() , deptLevelDto) ;
            //并且将所有一级部门装入rootList中
            if(LevelUtil.ROOT.equals(deptLevelDto.getLevel())){
                rootDeptList.add(deptLevelDto) ;
            }

        }

        //将数据根据seq从小到大排序
        Collections.sort(rootDeptList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });

        //进行递归排序
        transformDeptTree(deptLevellist , LevelUtil.ROOT , levelDeptMap) ;
        return rootDeptList ;
    }

    /**
    *DESC: 将数据进行递归排序
    *@author hou.linan
    *@date:  2019/3/31 15:54
    *@param:  [deptLevelDtos-->当前结构   , level-->当前level , levelDeptMap---> map]
    *@return:  void
    */
    public void transformDeptTree(List<DeptLevelDto> deptLevelDtos  , String level , Multimap<String , DeptLevelDto> levelDeptMap){
        for(DeptLevelDto deptLevelDto : deptLevelDtos){
            //遍历该层的每个元素

            //处理当前的层级数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId() );
            //处理下一级
            List<DeptLevelDto> tempDeptList = ( List<DeptLevelDto>) levelDeptMap.get(nextLevel) ;
            if(CollectionUtils.isNotEmpty(tempDeptList)){
                //排序
                Collections.sort(tempDeptList ,deptSeqComparator  );
                //设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                //进入到下一层递归中处理
                transformDeptTree(tempDeptList , nextLevel , levelDeptMap);
            }
        }
    }

    //根据组织的seq比较器
    public Comparator<DeptLevelDto> deptSeqComparator =  new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


}
