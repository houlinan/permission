package cn.hgxsp.util;

import com.trs.common.Page;
import com.trs.util.CMyString;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class BaseService {

    @Autowired
    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager entityManager;

    /**
     * 保存对象数据 Description: 保存对象数据<BR>
     *
     * @param obj 数据对象
     * @author jin.yu
     * @date 2014-3-12 下午08:01:40
     * @version 1.0
     */
    public void save(Object obj) {
        entityManager.persist(obj);
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void newTransactionalSave(Object obj) {
        entityManager.persist(obj);
    }

    public void saveOrUpdate(Object obj) {
        entityManager.merge(obj);
    }

    /**
     * Description: 删除或更新传入的对象（方法已过时，更新操作请考虑批量更新） <BR>
     *
     * @param parameters
     * @author liu.zhuan
     * @date 2016年5月10日 下午4:24:24
     */
    @Deprecated
    public <T> void saveOrUpdateAll(List<T> parameters) {
        for (int i = 0; i < parameters.size(); i++) {
            Object obj = parameters.get(i);
            if (obj == null) {
                continue;
            }
            saveOrUpdate(obj);
        }

    }

    /**
     * 修改对象数据 Description: 修改对象数据<BR>
     *
     * @param obj 数据对象
     * @author jin.yu
     * @date 2014-3-12 下午08:10:11
     * @version 1.0
     */
    public void update(Object obj) {
        entityManager.merge(obj);
    }

    /**
     * Description: 删除对象数据<BR>
     *
     * @param obj 数据对象
     * @author jin.yu
     * @date 2014-3-12 下午08:13:03
     * @version 1.0
     */
    public void delete(Object obj) {
        entityManager.remove(entityManager.merge(obj));
    }

    /**
     * Description:批量删除对象（方法已过时）
     *
     * @param parameters
     * @author shenbin
     * @date 2016-8-19 上午10:18:32
     * @version 1.0
     */
    @Deprecated
    public void deleteAll(List<Object> parameters) {
        for (int i = 0; i < parameters.size(); i++) {
            Object obj = parameters.get(i);
            if (obj == null) {
                continue;
            }
            delete(obj);
        }
    }

    /**
     * 根据编号删除对象集合 Description: 根据编号删除对象集合<BR>
     *
     * @param sFrom      String 要删除的对象。如：AppUser,不包含from关键字
     * @param sFieldName String 要删除对象的条件字段名称。如：groupId
     * @param sIds       要删除对象的ID String 以","隔开。
     * @throws Exception
     * @author jin.yu
     * @date 2014-3-12 下午09:15:09
     * @version 1.0
     */
    @Deprecated
    public void deleteAll(String sFrom, String sFieldName, String sIds) throws Exception {
        List<Object> list = findByIds(sFrom, sFieldName, sIds);
        deleteAll(list);
    }

    public <T> List<T> find(String _sSql, List<Object> _params) {
        return find(_sSql, _params, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(String _sSql, List<Object> _params, int _nStartIndex, int _nPageSize) {
        Query query = entityManager.createQuery(_sSql);
        if (_params != null && !_params.isEmpty()) {
            for (int i = 0; i < _params.size(); i++) {
                query.setParameter(i + 1, _params.get(i));
            }
        }
        if (_nStartIndex >= 0 && _nPageSize > 0) {
            query.setFirstResult(_nStartIndex);
            query.setMaxResults(_nPageSize);
        }
        return query.getResultList();
    }

    /**
     * 通用分页查询 Description: 通用分页查询 <BR>
     * 此方法用于封装支持where条件，查询部分字段，排序方式，分页操作。 注意： 1.当需要查询部分字段时，需要在参数封装时实例化一个对象。
     * 如select new AppUser(param1,param2,...) from
     * AppUser，将返回对象的集合，前提是对象有支持该属性列表的构造方法。 或者select new
     * Map/List(param1,param2,...) from AppUser,将返回存有list/或map的集合对象。
     * 2.当没有设置字段列表selectfields时，将查询所有字段值。 3.当设置where条件后，必须输入参数列表parameters。
     * 4.当设置startpage和pagesize的值大于-1时，才会根据分页查询数据，否则返回所有对象集合。
     * 5.当设置排序方式order时，才会按字段值排序。
     *
     * @param sSelectFields String 查询字段 eq:不包含select关键字，如userId,userName，或new
     *                      list(userName)或new map(userName)。当查询全部字段时可以输入空值或字符串。
     * @param sFrom         String 查询对象名称 eq:不包含from关键字，如AppUser
     * @param sWhere        String 条件 eq:不包含where关键字，如userName = ? and trueName = ?
     *                      模糊查询时，需要在参数值中加入"%",where语句中不可以加类似username like
     *                      '%?%',正确方式为：username like ?
     * @param sOrder        String 排序 eq:不包含order by 关键字，如userId asc/desc
     * @param nStartIndex    int 数据开始数
     * @param nPageSize     int 每页几条
     * @param parameters    List 条件参数值
     * @return List 符合查询条件的对象集合
     * @throws Exception
     * @author liu.zhuan
     * @date 2014-3-10 下午09:18:38
     * @version 1.0
     */
    public <T> List<T> find(String sSelectFields, String sFrom, String sWhere, String sOrder, int nStartIndex,
                            int nPageSize, List<Object> parameters) throws Exception {
        StringBuffer hql = new StringBuffer();
        if (!CMyString.isEmpty(sSelectFields) && !"*".equals(sSelectFields)) {
            hql.append("select ").append(sSelectFields);
        }
        if (!CMyString.isEmpty(sFrom)) {
            hql.append(" from ").append(sFrom);
        } else {
            throw new Exception("要查询的对象名称没有输入！");
        }
        if (!CMyString.isEmpty(sWhere)) {
            hql.append(" where ").append(sWhere);
        }
        if (!CMyString.isEmpty(sOrder)) {
            hql.append(" order by ").append(sOrder);
        }
        if ((!CMyString.isEmpty(sWhere) && (sWhere.indexOf("?") > 0 || sWhere.indexOf(":") > 0))
                && parameters == null) {
            throw new Exception("要查询的对象的参数列表没有输入！");
        }
        if (CMyString.isEmpty(sWhere) && parameters != null && parameters.size() > 0) {
            throw new Exception("要查询的对象的字段列表没有输入！");
        }
        if (nStartIndex > -1 && nPageSize > -1) {
            return find(hql.toString(), parameters, nStartIndex, nPageSize);
        }
        return find(hql.toString(), parameters);
    }

    /**
     * 单个条件 查询 Description: 此方法只支持一个条件参数的查询<BR>
     *
     * @param sSelectFields String 查询字段 eq:不包含select关键字，如userId,userName或new
     *                      list(userName)或new map(userName)。当查询全部字段时可以输入空值或字符串。
     * @param sFrom         String 查询对象名称 eq:不包含from关键字，如AppUser
     * @param sWhere        String 条件 eq:不包含where关键字，如userName = ?
     * @param sOrder        String 排序 eq:不包含order by 关键字，如userId asc/desc
     * @param param         条件参数
     * @return List 符合查询条件的对象集合
     * @throws Exception
     * @author liu.zhuan
     * @date 2014-3-13 下午04:20:52
     * @version 1.0
     */
    public <T> List<T> find(String sSelectFields, String sFrom, String sWhere, String sOrder, Object param)
            throws Exception {
        return find(sSelectFields, sFrom, sWhere, sOrder, Collections.singletonList(param));
    }

    /**
     * 根据参数列表查询数据集合 Description: 根据参数列表查询数据集合 <BR>
     *
     * @param sSelectFields String 查询字段 eq:不包含select关键字，如userId,userName或new
     *                      list(userName)或new map(userName)。当查询全部字段时可以输入空值或字符串。
     * @param sFrom         String 查询对象名称 eq:不包含from关键字，如AppUser
     * @param sWhere        String 条件 eq:不包含where关键字，如userName = ? and trueName = ?
     * @param sOrder        String 排序 eq:不包含order by 关键字，如userId asc/desc
     * @param parameters    条件参数值
     * @return List 符合查询条件的对象集合
     * @throws Exception
     * @author liu.zhuan
     * @date 2014-3-10 下午09:18:38
     * @version 1.0
     */
    public <T> List<T> find(String sSelectFields, String sFrom, String sWhere, String sOrder, List<Object> parameters)
            throws Exception {
        return find(sSelectFields, sFrom, sWhere, sOrder, -1, -1, parameters);
    }


    /**
     * 用于查询对象记录数,支持多个条件查询
     *
     * @param sFrom  String 查询对象名称 eq:不包含from关键字，如AppUser
     * @param sWhere String 条件 eq:不包含where关键字，如userName = ? and trueName = ?
     * @param params 参数值
     * @return Integer 记录数
     * @throws Exception
     * @Description: 用于查询对象记录数, 支持多个条件查询<BR>
     * @author jin.yu
     * @date 2014-3-12 下午02:00:07
     * @version 1.0
     */
    public Integer count(String sFrom, String sWhere, List<Object> params) throws Exception {
        return ((Long) find("count(*)", sFrom, sWhere, "", -1, -1, params).listIterator().next()).intValue();
    }

    /**
     * 用于查询对象记录数,单个条件查询
     *
     * @param sFrom  String 查询对象名称 eq:不包含from关键字，如AppUser
     * @param sWhere String 条件 eq:不包含where关键字，如userName = ?
     * @param param  参数值
     * @return Integer 记录数
     * @throws Exception
     * @Description: 此方法只支持单个条件查询<BR>
     * @author jin.yu
     * @date 2014-3-12 下午02:00:07
     * @version 1.0
     */
    public Integer count(String sFrom, String sWhere, Object param) throws Exception {
        return count(sFrom, sWhere, Collections.singletonList(param));
    }

    /**
     * Description: 查询对象记录数,支持多个条件查询 <BR>
     *
     * @param sCountSelect 可指定count(字段)，用于连表查询时指定字段
     * @param sFrom        表名
     * @param sWhere       where条件
     * @param params       sql参数
     * @return Integer数量
     * @throws Exception
     * @author liu.zhuan
     * @date 2017年12月1日 上午9:54:19
     * @version 1.0
     */
    public Integer count(String sCountSelect, String sFrom, String sWhere, List<Object> params) throws Exception {
        return ((Long) find(sCountSelect, sFrom, sWhere, "", -1, -1, params).listIterator().next()).intValue();
    }

    /**
     * Description: 获取分页列表 <BR>
     *
     * @param sSelectFields 查询字段 当查询全部字段时可以输入空值或字符串。
     * @param sFrom         查询对象
     * @param sWhere        条件
     * @param sOrder        排序
     * @param nStartPage    开始页数
     * @param nPageSize     每页几条
     * @param paramters    条件参数值
     * @throws Exception
     * @author liu.zhuan
     * @date 2014-3-12 下午02:40:40
     * @version 1.0
     */
    public <T> Page<T> findPage(String sSelectFields, String sFrom, String sWhere, String sOrder, int nStartPage,
                                int nPageSize, List<Object> paramters) throws Exception {
        int totalResults = count(sFrom, sWhere, paramters);
        Page<T> page = new Page<T>(nStartPage, nPageSize, totalResults);
        List<T> data = find(sSelectFields, sFrom, sWhere, sOrder, page.getStartIndex(), nPageSize, paramters);
        page.setLdata(data);
        return page;
    }
    
	/**
	 * 
	 * Description: <BR>
	 * 此方法适用于使用groupby分组的情况
	 * 
	 * @author liu.zhitong
	 * @date 2018年9月26日 下午2:03:16
	 * @param sSelectFields
	 *            查询字段 当查询全部字段时可以输入空值或字符串。
	 * @param sFrom
	 *            查询对象
	 * @param sWhere
	 *            条件
	 * @param sOrder
	 *            排序
	 * @param nStartPage
	 *            开始页数
	 * @param nPageSize
	 *            每页几条
	 * @param paramters
	 *            条件参数值
	 * @return
	 * @throws Exception
	 * @version 1.0
	 */
	public <T> Page<T> findPageForGroupBy(String sSelectFields, String sFrom, String sWhere, String sOrder,
			int nStartPage, int nPageSize, List<Object> paramters) throws Exception {
		List<Object> list = find("count(*)", sFrom, sWhere, "", -1, -1,paramters);
		int totalResults = list == null ? 0:list.size();
		Page<T> page = new Page<T>(nStartPage, nPageSize, totalResults);
		List<T> data = find(sSelectFields, sFrom, sWhere, sOrder, page.getStartIndex(), nPageSize, paramters);
		page.setLdata(data);
		return page;
	}

    /**
     * Description: 分页查询（适用于关联查询时需要指定查询数据总数的字段） <BR>
     *
     * @param sSelectFields     字段列表
     * @param sCountSelectField 查询数据总数的字段
     * @param sFrom             实体名称
     * @param sWhere            查询条件
     * @param sOrder            排序
     * @param nStartPage        开始页码
     * @param nPageSize         每页几条
     * @param paramters         查询参数
     * @return
     * @throws Exception
     * @author liu.zhuan
     * @date 2018年1月11日 下午3:36:35
     * @version 1.0
     */
    public <T> Page<T> findPageForJoinQuery(String sSelectFields, String sCountSelectField, String sFrom, String sWhere,
                                            String sOrder, int nStartPage, int nPageSize, List<Object> paramters) throws Exception {
        int totalResults = count("count(" + sCountSelectField + ")", sFrom, sWhere, paramters);
        Page<T> page = new Page<T>(nStartPage, nPageSize, totalResults);
        List<T> data = find(sSelectFields, sFrom, sWhere, sOrder, page.getStartIndex(), nPageSize, paramters);
        page.setLdata(data);
        return page;
    }


    /**
     * 用于查询单个对象 Description: 用于查询单个对象 <BR>
     *
     * @param clazz 查询对象Class eq:如AppUser.class
     * @param id      对象主键ID
     * @return Object
     * @throws Exception
     * @author liu.zhuan
     * @date 2014-3-10 下午05:19:17
     * @version 1.0
     */
    public <T> T findById(Class<T> clazz, Long id) {
        return entityManager.find(clazz, id);
    }

    /**
     * 用于查询单个对象 Description: 用于查询单个对象 <BR>
     *
     * @param clazz 查询对象Class eq:如AppUser.class
     * @param id      对象主键ID
     * @return Object
     * @throws Exception
     * @author liu.zhuan
     * @date 2014-3-10 下午05:19:17
     * @version 1.0
     */
    public <T> T findById(Class<T> clazz, Integer id) {
        return entityManager.find(clazz, id);
    }

    /**
     * 根据对象ID集合查询数据对象集合 Description: 根据对象ID集合查询数据对象集合<BR>
     *
     * @param sFrom  String 要查询对象名称 eq:不需要select关键字，如AppUser
     * @param sField String 要查询的字段名称 eq:如userId
     * @param ids    List 要查询的对象ID集合
     * @return List 符合条件的对象集合
     * @throws Exception
     * @author jin.yu
     * @date 2014-3-17 下午02:52:54
     * @version 1.0
     */
    public <T> List<T> findByIds(final String sFrom, final String sField, final List<Object> ids) throws Exception {
        StringBuffer hql = new StringBuffer();
        if (CMyString.isEmpty(sFrom)) {
            throw new Exception("要查询的对象名称没有输入！");
        }
        if (CMyString.isEmpty(sField)) {
            throw new Exception("要查询的对象的字段列表没有输入！");
        }
        if (ids == null || ids.size() == 0) {
            throw new Exception("要查询的对象的参数列表没有输入！");
        }
        hql.append(" from ").append(sFrom).append(" where ").append(sField).append(" in(:ids) ");
        return findByIds(hql.toString(), ids);

    }

    /**
     * @param _sHql
     * @param ids
     * @return
     * @throws Exception
     */
    public <T> List<T> findByIds(final String _sHql, final List<Object> ids) throws Exception {
        Query query = entityManager.createQuery(_sHql);
        query.setParameter("ids", ids);
        List list = query.getResultList();
        return list;
    }

    /**
     * 通过ID字符串查询多条数据信息 Description: 通过ID字符串查询多条数据信息<BR>
     *
     * @param sFrom  String 要查询对象名称 eq:不需要select关键字，如AppUser
     * @param sField String 要查询的字段名称 eq:如userId
     * @param sIds    String 要查询的对象ID值，以","隔开
     * @return List 符合条件的对象集合
     * @throws Exception
     * @author jin.yu
     * @date 2014-3-17 下午03:06:17
     * @version 1.0
     */
    public <T> List<T> findByIds(String sFrom, String sField, String sIds) throws Exception {
        if (CMyString.isEmpty(sIds)) {
            throw new Exception("要查询的对象ID值没有输入！");
        }
        String[] arrIds = sIds.split(",");
        // 将传入的Id组装成一个数组
        List<Object> idsList = new ArrayList<Object>();
        for (int i = 0; i < arrIds.length; i++) {
            idsList.add(Integer.parseInt(arrIds[i]));
        }
        return findByIds(sFrom, sField, idsList);
    }

    /**
     * 通过ID字符串查询多条数据信息 Description: 通过ID字符串查询多条数据信息<BR>
     *
     * @param sSelectFields String 要查询的字段列表 eq：不需要select关键字
     * @param sFrom         String 要查询对象名称 eq:不需要from关键字，如AppUser
     * @param sIn           String 不包括where 和 in关键字，只需输入字段名称 eq:如userId
     * @param sIds           String 要查询的对象ID值，以","隔开
     * @return List 符合条件的对象集合
     * @throws Exception
     * @author jin.yu
     * @date 2014-3-17 下午03:06:17
     * @version 1.0
     */
    public <T> List<T> findByIds(String sSelectFields, String sFrom, String sIn, String sIds) throws Exception {
        StringBuffer hql = new StringBuffer();
        if (CMyString.isEmpty(sIds)) {
            throw new Exception("要查询的对象ID值没有输入！");
        }
        if (CMyString.isEmpty(sFrom)) {
            throw new Exception("要查询的对象名称没有输入！");
        }
        if (CMyString.isEmpty(sIn)) {
            throw new Exception("要查询的对象的字段列表没有输入！");
        }
        if (!CMyString.isEmpty(sSelectFields)) {
            hql.append("select ").append(sSelectFields);
        }
        hql.append(" from ").append(sFrom).append(" where ").append(sIn).append(" in(:ids) ");
        String[] arrIds = sIds.split(",");
        // 将传入的Id组装成一个数组
        List<Object> idsList = new ArrayList<Object>();// (List)Arrays.asList(arrIds);
        for (int i = 0; i < arrIds.length; i++) {
            idsList.add(Integer.parseInt(arrIds[i]));
        }
        // System.out.println(hql.toString());
        return findByIds(hql.toString(), idsList);// findByIds(sFrom,
        // sField, idsList);
    }

    /**
     * 根据对象名，条件获取对象信息 Description: 根据对象名，条件获取对象信息,支持单个条件查询<BR>
     *
     * @param sFrom  String 查询对象名称 eq:不包含from关键字，如AppUser
     * @param sWhere String 条件 eq:不包含where关键字，如userName = ?
     * @param param  条件值
     * @return Object 符合条件的对象
     * @throws Exception
     * @author liu.zhuan
     * @date 2014-3-7 下午05:25:16
     * @version 1.0
     */
    @SuppressWarnings("unchecked")
    public <T> T findObject(String sFrom, String sWhere, Object param) throws Exception {
        List<Object> dataList = find("", sFrom, sWhere, "", Collections.singletonList(param));
        if (dataList != null && dataList.size() > 0) {
            return (T) dataList.iterator().next();
        }
        return null;
    }

    /**
     * 根据对象名，条件获取对象信息 Description: 根据对象名，条件获取对象信息 ,支持多条件查询<BR>
     *
     * @param sFrom     String 查询对象名称 eq:不包含from关键字，如AppUser
     * @param sWhere    String 条件 eq:不包含where关键字，如userName = ? and trueName = ?
     * @param paramters List条件值
     * @return Object 符合条件的对象
     * @throws Exception
     * @author jin.yu
     * @date 2014-3-17 下午07:36:16
     * @version 1.0
     */
    @SuppressWarnings("unchecked")
    public <T> T findObject(String sFrom, String sWhere, List<Object> paramters) throws Exception {
        List<Object> dataList = find("", sFrom, sWhere, "", paramters);
        if (dataList != null && dataList.size() > 0) {
            return (T) dataList.iterator().next();
        }
        return null;
    }

    /**
     * 判断数据在表中是否有重复 Description: 判断数据在表中是否有重复,支持多条件查询<BR>
     *
     * @param sFrom     表对象 eq：AppUser
     * @param sWhere    查询条件 eq：username = ? and userId = ?
     * @param paramters 查询条件的值
     * @return boolean true表示数据存在，false表示不存在
     * @throws Exception
     * @author jin.yu
     * @date 2014-3-13 下午08:55:03
     * @version 1.0
     */
    public boolean existData(String sFrom, String sWhere, List<Object> paramters) throws Exception {
        int userCount = count(sFrom, sWhere, paramters);
        if (userCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * Description: 判断数据在表中是否有重复，单条件查询<BR>
     *
     * @param sFrom    表对象 eq：AppUser
     * @param sWhere   查询条件 eq：username = ?
     * @param paramter 查询条件的值
     * @return boolean true表示数据存在，false表示不存在
     * @throws Exception
     * @author jin.yu
     * @date 2014-3-13 下午08:55:03
     * @version 1.0
     */
    public boolean existData(String sFrom, String sWhere, Object paramter) throws Exception {
        int userCount = count(sFrom, sWhere, paramter);
        if (userCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * Description: 执行hql语句 <BR>
     *
     * @param _sHql
     * @return
     * @author liu.zhuan
     * @date 2017年11月20日 上午11:25:19
     * @version 1.0
     */
    public int executeHql(String _sHql, List<Object> _params) {
        Query query = entityManager.createQuery(_sHql);
        if (_params != null && _params.size() > 0) {
            for (int i = 0; i < _params.size(); i++) {
                query.setParameter(i + 1, _params.get(i));
            }
        }
        return query.executeUpdate();
    }

    /**
     * Description: 执行原始sql <BR>
     *
     * @param sql
     * @return
     * @throws SQLException
     * @author liu.zhuan
     * @date 2017年11月20日 上午10:43:51
     * @version 1.0
     */
    public int executeBaseSql(String sql) throws SQLException {
        Query query = entityManager.createNativeQuery(sql);
        return query.executeUpdate();
    }
    /**
     * Description: 执行原生sql（带参数） <BR>
     *
     * @author liu.zhuan
     * @date 2018年4月11日 上午10:54:49
     * @param sql
     * @param params
     * @return
     * @version 1.0
     */
    public int executeBaseSql(String sql, List<Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
        }
        return query.executeUpdate();
    }

    public List<Map> executeQuerySql(String sql, Map<String, Object> parameters, String sort, int firstResult,int maxResults) {
        if (!CMyString.isEmpty(sort)) {
            sql = sql + " order by " + sort;
        }
        Query query = entityManager.createNativeQuery(sql);
        if (parameters != null) {
            Set<Map.Entry<String, Object>> set = parameters.entrySet();
            for (Map.Entry<String, Object> entry : set) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (firstResult >= 0 && maxResults >= 0) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }



}
