package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId,paths);
        Collections.reverse(parentPath);
        return (Long[])parentPath.toArray(new Long[parentPath.size()]);
    }
    /**
     * 级联更新所有关联数据
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return categoryEntities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getcatalogJson() {
        //1. 查出所有的一级分类
        List<CategoryEntity> level1Categorys = getLevel1Categorys();

        //2. 封装数据
        Map<String,List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k->k.getCatId().toString(),v->{
            //1. 每一个的一级分类,查到这个一级分类的二级分类
            List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getParentCid()));
            //2. 封装上面的结果
            List<Catelog2Vo> catelog2Vos = null;
            if(categoryEntities!=null){
                catelog2Vos = categoryEntities.stream().map(l2 ->{
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(),null,l2.getCatId().toString(),l2.getName().toString());
                    //1. 找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> level3Catalog = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid",l2.getParentCid()));
                    if(level3Catalog!=null){
                        List<Catelog2Vo.Catelog3Vo> collect = level3Catalog.stream().map(l3 ->{
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString() , l3.getCatId().toString(), l3.getName().toString());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        return parent_cid;
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        //1、收集当前节点id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1.查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //2.组装父子树形结构
        //2.1找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,entities));
            return menu;
        }).sorted((menu1,menu2) ->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return level1Menus;
    }
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter(categoryEntity ->{
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            //1.找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            //2.菜单的排序
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {
        //ToDo: 1.检查要删除的内容是否被引用
        baseMapper.deleteBatchIds(asList);
    }
}