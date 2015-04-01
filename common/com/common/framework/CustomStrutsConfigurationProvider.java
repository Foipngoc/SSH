package com.common.framework;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.util.location.LocatableProperties;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 解决struts2中自动加载模块的struts的配置文件
 * <p>
 * 注意：模块的配置文件必须为*.struts.xml
 * <p>
 * 
 * @author Wall.Lee(xiaoshan2242@gmail.com)
 */
public class CustomStrutsConfigurationProvider extends XmlConfigurationProvider {
	private static final String FILE_PATTERN = "classpath*:/**/*.struts.xml";
	private static final Logger LOG = LoggerFactory
			.getLogger(CustomStrutsConfigurationProvider.class);

	public CustomStrutsConfigurationProvider() {
		Map<String, String> mappings = new HashMap<String, String>();
		mappings.put("-//OpenSymphony Group//XWork 2.1.3//EN",
				"xwork-2.1.3.dtd");
		mappings.put("-//OpenSymphony Group//XWork 2.1//EN", "xwork-2.1.dtd");
		mappings.put("-//OpenSymphony Group//XWork 2.0//EN", "xwork-2.0.dtd");
		mappings.put("-//OpenSymphony Group//XWork 1.1.1//EN",
				"xwork-1.1.1.dtd");
		mappings.put("-//OpenSymphony Group//XWork 1.1//EN", "xwork-1.1.dtd");
		mappings.put("-//OpenSymphony Group//XWork 1.0//EN", "xwork-1.0.dtd");
		mappings.put(
				"-//Apache Software Foundation//DTD Struts Configuration 2.0//EN",
				"struts-2.0.dtd");
		mappings.put(
				"-//Apache Software Foundation//DTD Struts Configuration 2.1//EN",
				"struts-2.1.dtd");
		mappings.put(
				"-//Apache Software Foundation//DTD Struts Configuration 2.1.7//EN",
				"struts-2.1.7.dtd");
		setDtdMappings(mappings);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.config.ContainerProvider#needsReload()
	 */
	@Override
	public boolean needsReload() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.config.ContainerProvider#register(com.opensymphony
	 * .xwork2.inject.ContainerBuilder,
	 * com.opensymphony.xwork2.util.location.LocatableProperties)
	 */
	@Override
	public void register(ContainerBuilder containerBuilder,
			LocatableProperties props) throws ConfigurationException {
		super.register(containerBuilder, props);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.config.PackageProvider#loadPackages()
	 */
	@Override
	public void loadPackages() throws ConfigurationException {
		super.loadPackages();
	}

	@Override
	protected Iterator<URL> getConfigurationUrls(String fileName)
			throws IOException {
		List<URL> urls = new ArrayList<URL>();
		Resource[] resources = getAllResourcesUrl();
		for (Resource resource : resources) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Loading struts2 configuration url: \r\n"
						+ resource.getURL());
			}
			urls.add(resource.getURL());
		}

		return urls.iterator();
	}

	/**
	 * 获取系统中需要搜寻的struts的配置
	 * 
	 * @return
	 * @throws IOException
	 */
	private Resource[] getAllResourcesUrl() {
		ResourcePatternResolver resoler = new PathMatchingResourcePatternResolver();
		try {
			return resoler.getResources(FILE_PATTERN);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Resource[0];
	}
}
