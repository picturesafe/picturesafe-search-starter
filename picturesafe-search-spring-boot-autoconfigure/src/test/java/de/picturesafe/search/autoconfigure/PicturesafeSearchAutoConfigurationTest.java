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

import de.picturesafe.search.elasticsearch.FieldConfigurationProvider;
import de.picturesafe.search.elasticsearch.IndexPresetConfigurationProvider;
import de.picturesafe.search.elasticsearch.config.QueryConfiguration;
import de.picturesafe.search.elasticsearch.config.RestClientConfiguration;
import de.picturesafe.search.elasticsearch.config.impl.StandardIndexPresetConfiguration;
import de.picturesafe.search.elasticsearch.connect.aggregation.resolve.FacetConverterChain;
import de.picturesafe.search.elasticsearch.connect.aggregation.search.AggregationBuilderFactoryRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {PicturesafeSearchAutoClientConfiguration.class, PicturesafeSearchAutoIndexConfiguration.class,
        PicturesafeSearchAutoQueryConfiguration.class, PicturesafeSearchAutoAggregationConfiguration.class})
public class PicturesafeSearchAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(PicturesafeSearchAutoClientConfiguration.class));

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
    private FacetConverterChain facetConverterChain;

    @Autowired
    private AggregationBuilderFactoryRegistry aggregationBuilderFactoryRegistry;

    @Test
    public void restClientConfigurationIsNotNull() {
        assertNotNull(restClientConfiguration);
    }

    @Test
    public void standardIndexPresetConfigurationIsNotNull() {
        assertNotNull(standardIndexPresetConfiguration);
    }

    @Test
    public void indexPresetConfigurationProviderIsNotNull() {
        assertNotNull(indexPresetConfigurationProvider);
    }

    @Test
    public void fieldConfigurationProviderIsNotNull() {
        assertNotNull(fieldConfigurationProvider);
    }

    @Test
    public void queryConfigurationIsNotNull() {
        assertNotNull(queryConfiguration);
    }

    @Test
    public void aggregationBuilderFactoryRegistryIsNotNull() {
        assertNotNull(aggregationBuilderFactoryRegistry);
    }

    @Test
    public void facetConverterChainIsNotNull() {
        assertNotNull(facetConverterChain);
    }

    @Test
    public void elasticsearchPortCanBeConfigured() {
        this.contextRunner.withPropertyValues("elasticsearch.hosts:localhost:9500").run((context) -> {
            assertNotNull(context.getBean(RestClientConfiguration.class));
            assertEquals("localhost:9500", context.getBean(RestClientConfiguration.class).getHostAddresses());
        });
    }
}
