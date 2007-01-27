/*
 * AdSapient - Open Source Ad Server
 * http://www.sourceforge.net/projects/adsapient
 * http://www.adsapient.com
 *
 * Copyright (C) 2001-06 Vitaly Sazanovich
 * Vitaly.Sazanovich@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Library General Public License  as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package com.adsapient.gui.forms;

import com.adsapient.api_impl.filter.BehaviorPattern;
import com.adsapient.api_impl.share.Category;

import com.adsapient.util.LabelValueUtil;
import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;


public class BehaviorActionForm extends ActionForm {
    private static final long serialVersionUID = 1L;
    private String action;
    private int recency;
    private int frequencyDays;
    private int frequencyNumbers;
    private int duration;
    private int id;
    private String name;
    private Collection templatesCollection = new ArrayList();
    private Collection numbersCollection = new ArrayList();
    private Collection categorysCollection = new ArrayList();
    private Collection categorysCollection2 = new ArrayList();
    private String selectedCategorys = "";
    private String keyWords = "";
    private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public void init(BehaviorPattern pattern) {
        if (pattern.getId() != null) {
            this.setId(pattern.getId().intValue());
        }

        this.setRecency(pattern.getRecency());
        this.setDuration(pattern.getDuration());
        this.setName(pattern.getName());
        this.setFrequencyDays(pattern.getFrequencyDay());
        this.setFrequencyNumbers(pattern.getFrequencyNumbers());
        this.selectedCategorys = pattern.getSelectedCategorys();
        this.keyWords = pattern.getKeyWords();

        Collection categoryCollection = MyHibernateUtil.viewAll(Category.class);

        Iterator iter = categoryCollection.iterator();

        while (iter.hasNext()) {
            Category category = (Category) iter.next();
            LabelValueBean bean = new LabelValueBean(category.getName(),
                    category.getId().toString());

            StringBuffer categoryStringBufer = new StringBuffer();

            categoryStringBufer.append(":").append(category.getId()).append(":");

            if (this.selectedCategorys.indexOf(categoryStringBufer.toString()) > -1) {
                this.getCategorysCollection2().add(bean);
            }

            this.getCategorysCollection().add(bean);
        }
    }

    public void update(BehaviorPattern pattern) {
        pattern.setName(this.getName());
        pattern.setRecency(this.getRecency());
        pattern.setDuration(this.getDuration());
        pattern.setFrequencyDay(this.getFrequencyDays());
        pattern.setFrequencyNumbers(this.getFrequencyNumbers());
        pattern.setKeyWords(this.getKeyWords());
        pattern.setSelectedCategorys(this.getSelectedCategorys());
    }

    public Collection getFrequencyCollection() {
        ArrayList list = (ArrayList) LabelValueUtil.fillCustomWeightCollection(1,
                32);

        list.add(0,
            new LabelValueBean(Msg.fetch("select.ifnecessary", this.request),
                Integer.toString(0)));

        return list;
    }

    public Collection getNumbersCollection() {
        return LabelValueUtil.fillCustomWeightCollection(1, 32);
    }

    public Collection getDurationCollection() {
        ArrayList list = (ArrayList) LabelValueUtil.fillCustomWeightCollection(1,
                61);

        list.add(0,
            new LabelValueBean(Msg.fetch("select.ifnecessary", this.request),
                Integer.toString(0)));

        return list;
    }

    public void setNumbersCollection(Collection numbersCollection) {
        this.numbersCollection = numbersCollection;
    }

    public Collection getTemplatesCollection() {
        return templatesCollection;
    }

    public void setTemplatesCollection(Collection templatesCollection) {
        this.templatesCollection = templatesCollection;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFrequencyDays() {
        return frequencyDays;
    }

    public void setFrequencyDays(int frequencyDays) {
        this.frequencyDays = frequencyDays;
    }

    public int getFrequencyNumbers() {
        return frequencyNumbers;
    }

    public void setFrequencyNumbers(int frequencyNumbers) {
        this.frequencyNumbers = frequencyNumbers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecency() {
        return recency;
    }

    public void setRecency(int recency) {
        this.recency = recency;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Collection getCategorysCollection() {
        return categorysCollection;
    }

    public void setCategorysCollection(Collection categorysCollection) {
        this.categorysCollection = categorysCollection;
    }

    public Collection getCategorysCollection2() {
        return categorysCollection2;
    }

    public void setCategorysCollection2(Collection categorysCollection2) {
        this.categorysCollection2 = categorysCollection2;
    }

    public String getSelectedCategorys() {
        return selectedCategorys;
    }

    public void setSelectedCategorys(String selectedCategorys) {
        this.selectedCategorys = selectedCategorys;
    }
}
