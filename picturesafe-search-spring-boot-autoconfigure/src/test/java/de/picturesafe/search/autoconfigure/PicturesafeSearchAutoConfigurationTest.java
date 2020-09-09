/*
 * Copyright 2020 picturesafe media/data/bank GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.picturesafe.search.autoconfigure;

import de.picturesafe.search.elasticsearch.ElasticsearchService;
import de.picturesafe.search.elasticsearch.FieldConfigurationProvider;
import de.picturesafe.search.elasticsearch.IndexPresetConfigurationProvider;
import de.picturesafe.search.elasticsearch.config.QueryConfiguration;
import de.picturesafe.search.elasticsearch.config.RestClientConfiguration;
import de.picturesafe.search.elasticsearch.config.impl.StandardIndexPresetConfiguration;
import de.picturesafe.search.elasticsearch.connect.aggregation.resolve.FacetConverterChain;
import de.picturesafe.search.elasticsearch.connect.aggregation.search.AggregationBuilderFactoryRegistry;
import de.picturesafe.search.elasticsearch.connect.filter.FilterFactory;
import de.picturesafe.search.elasticsearch.connect.query.QueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.FilteredClassLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PicturesafeSearchAutoConfigurationTest extends AbstractPicturesafeSearchConfigurationTest {

    @Autowired
    private RestClientConfiguration restClientConfiguration;

    @Autowired
    private StandardIndexPresetConfiguration standardIndexPresetConfiguration;

    @Autowired
    private IndexPresetConfigurationProvider indexPresetConfigurationProvider;

    @Autowired
    private FieldConfigurationProvider fieldConfigurationProvider;

    @Autowired
    private QueryConfiguration queryConfiguration;

    @Autowired
    @Qualifier("queryFactories")
    private List<QueryFactory> queryFactories;

    @Autowired
    @Qualifier("filterFactories")
    private List<FilterFactory> filterFactories;

    @Autowired
    private FacetConverterChain facetConverterChain;

    @Autowired
    private AggregationBuilderFactoryRegistry aggregationBuilderFactoryRegistry;

    @Test
    public void restClientConfigurationIsNotNull() {
        assertThat(restClientConfiguration).isNotNull();
    }

    @Test
    public void standardIndexPresetConfigurationIsNotNull() {
        assertThat(standardIndexPresetConfiguration).isNotNull();
    }

    @Test
    public void indexPresetConfigurationProviderIsNotNull() {
        assertThat(indexPresetConfigurationProvider).isNotNull();
    }

    @Test
    public void fieldConfigurationProviderIsNotNull() {
        assertThat(fieldConfigurationProvider).isNotNull();
    }

    @Test
    public void queryConfigurationIsNotNull() {
        assertThat(queryConfiguration).isNotNull();
    }

    @Test
    public void queryFactoriesIsNotNull() {
        assertThat(queryFactories).isNotEmpty();
    }

    @Test
    public void filterFactoriesIsNotNull() {
        assertThat(filterFactories).isNotEmpty();
    }

    @Test
    public void aggregationBuilderFactoryRegistryIsNotNull() {
        assertThat(aggregationBuilderFactoryRegistry).isNotNull();
    }

    @Test
    public void facetConverterChainIsNotNull() {
        assertThat(facetConverterChain).isNotNull();
    }

    @Test
    public void elasticsearchHostIsAutoConfigured() {
        assertThat(restClientConfiguration.getHostAddresses()).isEqualTo("localhost:9200");
    }

    @Test
    public void elasticsearchHostCanBeConfigured() {
        this.clientConfigurationContextRunner.withPropertyValues("elasticsearch.hosts:localhost:9500").run((context) -> {
            assertThat(context).hasSingleBean(RestClientConfiguration.class);
            assertThat(context.getBean(RestClientConfiguration.class).getHostAddresses()).isEqualTo("localhost:9500");
        });
    }

    @Test
    public void restClientBeanIsIgnoredIfLibraryIsNotPresent() {
        this.clientConfigurationContextRunner.withClassLoader(new FilteredClassLoader(ElasticsearchService.class))
                .run((context) -> assertThat(context).doesNotHaveBean("restClientConfiguration"));
    }
}
