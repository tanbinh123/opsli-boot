/**
 * Copyright 2020 OPSLI 快速开发平台 https://www.opsli.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.opsli.common.utils;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取resources下的yaml配置类
 *
 *  使用的maven包
 *  <dependency>
 *       <groupId>org.yaml</groupId>
 *       <artifactId>snakeyaml</artifactId>
 *       <version>1.25</version>
 *  </dependency>
 *  map->bean使用此包
 *  <dependency>
 *       <groupId>commons-beanutils</groupId>
 *       <artifactId>commons-beanutils-core</artifactId>
 *       <version>1.8.3</version>
 *  </dependency>
 *
 *使用方法
 *eg:
 *  opsli:
 *   config:
 *       key: value
 *       list:
 *         - 张三
 *         - 李四
 *       map:
 *         - name: 张三map
 *           age: 12
 *         - name: 李四map
 *           age: 121
 *
 *支持获取指定层级之后的数据,如"opsli.config" : new ConfigBean().prefix("opsli.config").getObj()
 *
 *支持获取list中指定数据,如"opsli.config.list.1" : new ConfigBean().prefix("opsli.config.list.1").getObj()
 *
 *支持获取list中指定数据之后的数据,如"opsli.config.map.1.name" :new ConfigBean().prefix("opsli.config.list.1").getString()
 *
 *
 * 针对 hutool 工具类 Props不是很有好， 重新写了自定义Yaml工具类
 * @author Parker
 */
@Slf4j
public class Props {

    /** 缓存 Prop 防止重复开启IO 注： 多线程安全 */
    private static final Map<String, Map<String, Object>> CACHE_MAP = new ConcurrentHashMap<>();

    /** 文件名 */
    private final String fileName;

    /**
     * 获取的对象
     */
    private Map<String, Object> temp;

    public Props(String fileName){
        this.fileName = fileName;
        this.load();
    }

    /**
     * 加载指定的文件
     */
    private void load() {

        if(CACHE_MAP.get(this.fileName) != null){
            temp = CACHE_MAP.get(this.fileName);
            return;
        }

        try (InputStream inputStream = Props.class.getResourceAsStream("/" + this.fileName)) {
            Yaml yaml = new Yaml();
            temp = yaml.load(inputStream);

            // 初始化保护
            if(temp == null){
                temp = new LinkedHashMap<>();
            }

            CACHE_MAP.put(this.fileName, temp);

        } catch (Exception e) {
            log.error("load config file {} error", this.fileName, e);
        }
    }


    /**
     * 获得 Obj对象
     * @return
     */
    public Object getObj(String key){
        return this.getObj(key, null);
    }

    /**
     * 获得 Obj对象
     * @return
     */
    public Object getObj(String key, Object def){
        if(key == null || "".equals(key) ){
            return def;
        }
        // key 集合
        String[] keys = key.split("\\.");

        // 获得对象
        Object obj = this.getObject(keys);

        if(obj != null){
            def = Convert.convert(def.getClass(), obj);
        }
        return def;
    }


    /**
     * 获得 字符串类型对象
     * @return
     */
    public String getStr(String key){
        return this.getStr(key, null);
    }

    /**
     * 获得 字符串类型对象
     * @return
     */
    public String getStr(String key, String def){
        if(key == null || "".equals(key) ){
            return def;
        }
        // key 集合
        String[] keys = key.split("\\.");

        // 获得对象
        Object obj = this.getObject(keys);

        if(obj != null){
            def = Convert.convert(def.getClass(), obj);
        }
        return def;
    }

    /**
     * 获得 Int 类型对象
     * @return
     */
    public Integer getInt(String key){
        return this.getInt(key, null);
    }

    /**
     * 获得 Int 类型对象
     * @return
     */
    public Integer getInt(String key, Integer def){
        if(key == null || "".equals(key) ){
            return def;
        }
        // key 集合
        String[] keys = key.split("\\.");

        // 获得对象
        Object obj = this.getObject(keys);

        if(obj != null){
            def = Convert.convert(def.getClass(), obj);
        }
        return def;
    }

    /**
     * 获得 Long 类型对象
     * @return
     */
    public Long getLong(String key){
        return this.getLong(key, null);
    }

    /**
     * 获得 Long 类型对象
     * @return
     */
    public Long getLong(String key, Long def){
        if(key == null || "".equals(key) ){
            return def;
        }
        // key 集合
        String[] keys = key.split("\\.");

        // 获得对象
        Object obj = this.getObject(keys);

        if(obj != null){
            def = Convert.convert(def.getClass(), obj);
        }
        return def;
    }


    /**
     * 获得 Double 类型对象
     * @return
     */
    public Double getDouble(String key){
        return this.getDouble(key, null);
    }

    /**
     * 获得 Double 类型对象
     * @return
     */
    public Double getDouble(String key, Double def){
        if(key == null || "".equals(key) ){
            return def;
        }
        // key 集合
        String[] keys = key.split("\\.");

        // 获得对象
        Object obj = this.getObject(keys);

        if(obj != null){
            def = Convert.convert(def.getClass(), obj);
        }
        return def;
    }



    /**
     * 获得 Float 类型对象
     * @return
     */
    public Float getFloat(String key){
        return this.getFloat(key, null);
    }

    /**
     * 获得 Float 类型对象
     * @return
     */
    public Float getFloat(String key, Float def){
        if(key == null || "".equals(key) ){
            return def;
        }
        // key 集合
        String[] keys = key.split("\\.");

        // 获得对象
        Object obj = this.getObject(keys);

        if(obj != null){
            def = Convert.convert(def.getClass(), obj);
        }
        return def;
    }


    /**
     * 获得 Char 类型对象
     * @return
     */
    public Character getChar(String key){
        return this.getChar(key, null);
    }

    /**
     * 获得 Char 类型对象
     * @return
     */
    public Character getChar(String key, Character def){
        if(key == null || "".equals(key) ){
            return def;
        }
        // key 集合
        String[] keys = key.split("\\.");

        // 获得对象
        Object obj = this.getObject(keys);

        if(obj != null){
            def = Convert.convert(def.getClass(), obj);
        }
        return def;
    }

    /**
     * 获得 布尔类型对象
     * @return
     */
    public Boolean getBool(String key){
        return this.getBool(key, null);
    }

    /**
     * 获得 布尔类型对象
     * @return
     */
    public Boolean getBool(String key, Boolean def){
        if(key == null || "".equals(key) ){
            return def;
        }
        // key 集合
        String[] keys = key.split("\\.");

        // 获得对象
        Object obj = this.getObject(keys);

        if(obj != null){
            def = Convert.convert(def.getClass(), obj);
        }
        return def;
    }


    /**
     * 获得对象
     * @param keys
     * @return
     */
    private Object getObject(String[] keys){
        // 循环查找
        Object obj = null;
        for (String k : keys) {
            if(obj == null){
                obj = temp.get(k);
            } else if(obj instanceof LinkedHashMap){
                obj = ((LinkedHashMap) obj).get(k);
            }
        }
        return obj;
    }

}