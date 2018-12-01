package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.WareList;

import java.io.Serializable;
import java.util.List;

/**
 * @author  gongchao
 */
public class WareListForm implements Serializable{
        private static final long serialVersionUID = -3021080975884963421L;
        private List<WareList> wareLists;

        public WareListForm() {
        }

        public List<WareList> getWareLists() {
            return wareLists;
        }

        public void setWareLists(List<WareList> wareLists) {
            this.wareLists = wareLists;
        }

        @Override
        public String toString() {
            return "WareListForm{" +
                    "wareLists=" + wareLists +
                    '}';
        }
}
