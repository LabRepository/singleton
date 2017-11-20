/**
 * Copyright 2011 Joao Miguel Pereira
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package eu.jpereira.trainings.designpatterns.creational.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.jpereira.trainings.designpatterns.creational.singleton.crwaling.CannotCrawlException;
import eu.jpereira.trainings.designpatterns.creational.singleton.crwaling.DummySiteCrawler;
import eu.jpereira.trainings.designpatterns.creational.singleton.crwaling.SiteCrawler;


/**
 * @author Joao Pereira
 *
 */
public enum ReportBuilderEnum {
    repotrbuilder;
    private SiteCrawler siteCrawler;
    private Map<String, StringBuffer> sitesContens;
    private List<String> configuredSites;

    {
        configuredSites = new ArrayList<String>();
        configuredSites.add("http://www.wikipedia.com");
        configuredSites.add("http://jpereira.eu");
        configuredSites.add("http://stackoverflow.com");
    }

    private ReportBuilderEnum(){
        this.siteCrawler = createSiteCrawler();

        // Now crawl some pre-defined sites
        for (String url : configuredSites) {
            this.siteCrawler.withURL(url);
        }
        try {
            this.setSitesContens(this.siteCrawler.crawl().packSiteContens());
        } catch (CannotCrawlException e) {
            // this singleton instance is in very bad shape... what wiil you do?
            // I cannot recover from this and this instance will be useless to
            // clients...
            throw new RuntimeException("Could not load sites:" + e.getMessage());
        }

    }
    public static ReportBuilderEnum getInstance() {
        return ReportBuilderEnum.repotrbuilder;

    }
    protected SiteCrawler createSiteCrawler() {

        return new DummySiteCrawler();
    }
    public Map<String, StringBuffer> getSitesContens() {
        return sitesContens;
    }

    /**
     * @param sitesContens
     *            the sitesContens to set
     */
    private void setSitesContens(Map<String, StringBuffer> sitesContens) {
        this.sitesContens = sitesContens;
    }
}