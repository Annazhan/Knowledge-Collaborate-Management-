package com.example.knw.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class KnwTeamExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public KnwTeamExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andTeamIdIsNull() {
            addCriterion("team_id is null");
            return (Criteria) this;
        }

        public Criteria andTeamIdIsNotNull() {
            addCriterion("team_id is not null");
            return (Criteria) this;
        }

        public Criteria andTeamIdEqualTo(Integer value) {
            addCriterion("team_id =", value, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamIdNotEqualTo(Integer value) {
            addCriterion("team_id <>", value, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamIdGreaterThan(Integer value) {
            addCriterion("team_id >", value, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("team_id >=", value, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamIdLessThan(Integer value) {
            addCriterion("team_id <", value, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamIdLessThanOrEqualTo(Integer value) {
            addCriterion("team_id <=", value, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamIdIn(List<Integer> values) {
            addCriterion("team_id in", values, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamIdNotIn(List<Integer> values) {
            addCriterion("team_id not in", values, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamIdBetween(Integer value1, Integer value2) {
            addCriterion("team_id between", value1, value2, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamIdNotBetween(Integer value1, Integer value2) {
            addCriterion("team_id not between", value1, value2, "teamId");
            return (Criteria) this;
        }

        public Criteria andTeamNameIsNull() {
            addCriterion("team_name is null");
            return (Criteria) this;
        }

        public Criteria andTeamNameIsNotNull() {
            addCriterion("team_name is not null");
            return (Criteria) this;
        }

        public Criteria andTeamNameEqualTo(String value) {
            addCriterion("team_name =", value, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameNotEqualTo(String value) {
            addCriterion("team_name <>", value, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameGreaterThan(String value) {
            addCriterion("team_name >", value, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameGreaterThanOrEqualTo(String value) {
            addCriterion("team_name >=", value, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameLessThan(String value) {
            addCriterion("team_name <", value, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameLessThanOrEqualTo(String value) {
            addCriterion("team_name <=", value, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameLike(String value) {
            addCriterion("team_name like", value, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameNotLike(String value) {
            addCriterion("team_name not like", value, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameIn(List<String> values) {
            addCriterion("team_name in", values, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameNotIn(List<String> values) {
            addCriterion("team_name not in", values, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameBetween(String value1, String value2) {
            addCriterion("team_name between", value1, value2, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamNameNotBetween(String value1, String value2) {
            addCriterion("team_name not between", value1, value2, "teamName");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateIsNull() {
            addCriterion("team_is_activate is null");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateIsNotNull() {
            addCriterion("team_is_activate is not null");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateEqualTo(Byte value) {
            addCriterion("team_is_activate =", value, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateNotEqualTo(Byte value) {
            addCriterion("team_is_activate <>", value, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateGreaterThan(Byte value) {
            addCriterion("team_is_activate >", value, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateGreaterThanOrEqualTo(Byte value) {
            addCriterion("team_is_activate >=", value, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateLessThan(Byte value) {
            addCriterion("team_is_activate <", value, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateLessThanOrEqualTo(Byte value) {
            addCriterion("team_is_activate <=", value, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateIn(List<Byte> values) {
            addCriterion("team_is_activate in", values, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateNotIn(List<Byte> values) {
            addCriterion("team_is_activate not in", values, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateBetween(Byte value1, Byte value2) {
            addCriterion("team_is_activate between", value1, value2, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andTeamIsActivateNotBetween(Byte value1, Byte value2) {
            addCriterion("team_is_activate not between", value1, value2, "teamIsActivate");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterionForJDBCDate("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andLeaderIdIsNull() {
            addCriterion("leader_id is null");
            return (Criteria) this;
        }

        public Criteria andLeaderIdIsNotNull() {
            addCriterion("leader_id is not null");
            return (Criteria) this;
        }

        public Criteria andLeaderIdEqualTo(Integer value) {
            addCriterion("leader_id =", value, "leaderId");
            return (Criteria) this;
        }

        public Criteria andLeaderIdNotEqualTo(Integer value) {
            addCriterion("leader_id <>", value, "leaderId");
            return (Criteria) this;
        }

        public Criteria andLeaderIdGreaterThan(Integer value) {
            addCriterion("leader_id >", value, "leaderId");
            return (Criteria) this;
        }

        public Criteria andLeaderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("leader_id >=", value, "leaderId");
            return (Criteria) this;
        }

        public Criteria andLeaderIdLessThan(Integer value) {
            addCriterion("leader_id <", value, "leaderId");
            return (Criteria) this;
        }

        public Criteria andLeaderIdLessThanOrEqualTo(Integer value) {
            addCriterion("leader_id <=", value, "leaderId");
            return (Criteria) this;
        }

        public Criteria andLeaderIdIn(List<Integer> values) {
            addCriterion("leader_id in", values, "leaderId");
            return (Criteria) this;
        }

        public Criteria andLeaderIdNotIn(List<Integer> values) {
            addCriterion("leader_id not in", values, "leaderId");
            return (Criteria) this;
        }

        public Criteria andLeaderIdBetween(Integer value1, Integer value2) {
            addCriterion("leader_id between", value1, value2, "leaderId");
            return (Criteria) this;
        }

        public Criteria andLeaderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("leader_id not between", value1, value2, "leaderId");
            return (Criteria) this;
        }

        public Criteria andBriefIsNull() {
            addCriterion("brief is null");
            return (Criteria) this;
        }

        public Criteria andBriefIsNotNull() {
            addCriterion("brief is not null");
            return (Criteria) this;
        }

        public Criteria andBriefEqualTo(String value) {
            addCriterion("brief =", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotEqualTo(String value) {
            addCriterion("brief <>", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefGreaterThan(String value) {
            addCriterion("brief >", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefGreaterThanOrEqualTo(String value) {
            addCriterion("brief >=", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefLessThan(String value) {
            addCriterion("brief <", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefLessThanOrEqualTo(String value) {
            addCriterion("brief <=", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefLike(String value) {
            addCriterion("brief like", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotLike(String value) {
            addCriterion("brief not like", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefIn(List<String> values) {
            addCriterion("brief in", values, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotIn(List<String> values) {
            addCriterion("brief not in", values, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefBetween(String value1, String value2) {
            addCriterion("brief between", value1, value2, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotBetween(String value1, String value2) {
            addCriterion("brief not between", value1, value2, "brief");
            return (Criteria) this;
        }

        public Criteria andInstituteIsNull() {
            addCriterion("institute is null");
            return (Criteria) this;
        }

        public Criteria andInstituteIsNotNull() {
            addCriterion("institute is not null");
            return (Criteria) this;
        }

        public Criteria andInstituteEqualTo(String value) {
            addCriterion("institute =", value, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteNotEqualTo(String value) {
            addCriterion("institute <>", value, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteGreaterThan(String value) {
            addCriterion("institute >", value, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteGreaterThanOrEqualTo(String value) {
            addCriterion("institute >=", value, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteLessThan(String value) {
            addCriterion("institute <", value, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteLessThanOrEqualTo(String value) {
            addCriterion("institute <=", value, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteLike(String value) {
            addCriterion("institute like", value, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteNotLike(String value) {
            addCriterion("institute not like", value, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteIn(List<String> values) {
            addCriterion("institute in", values, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteNotIn(List<String> values) {
            addCriterion("institute not in", values, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteBetween(String value1, String value2) {
            addCriterion("institute between", value1, value2, "institute");
            return (Criteria) this;
        }

        public Criteria andInstituteNotBetween(String value1, String value2) {
            addCriterion("institute not between", value1, value2, "institute");
            return (Criteria) this;
        }

        public Criteria andResourceSizeIsNull() {
            addCriterion("resource_size is null");
            return (Criteria) this;
        }

        public Criteria andResourceSizeIsNotNull() {
            addCriterion("resource_size is not null");
            return (Criteria) this;
        }

        public Criteria andResourceSizeEqualTo(Float value) {
            addCriterion("resource_size =", value, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andResourceSizeNotEqualTo(Float value) {
            addCriterion("resource_size <>", value, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andResourceSizeGreaterThan(Float value) {
            addCriterion("resource_size >", value, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andResourceSizeGreaterThanOrEqualTo(Float value) {
            addCriterion("resource_size >=", value, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andResourceSizeLessThan(Float value) {
            addCriterion("resource_size <", value, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andResourceSizeLessThanOrEqualTo(Float value) {
            addCriterion("resource_size <=", value, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andResourceSizeIn(List<Float> values) {
            addCriterion("resource_size in", values, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andResourceSizeNotIn(List<Float> values) {
            addCriterion("resource_size not in", values, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andResourceSizeBetween(Float value1, Float value2) {
            addCriterion("resource_size between", value1, value2, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andResourceSizeNotBetween(Float value1, Float value2) {
            addCriterion("resource_size not between", value1, value2, "resourceSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeIsNull() {
            addCriterion("used_size is null");
            return (Criteria) this;
        }

        public Criteria andUsedSizeIsNotNull() {
            addCriterion("used_size is not null");
            return (Criteria) this;
        }

        public Criteria andUsedSizeEqualTo(Float value) {
            addCriterion("used_size =", value, "usedSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeNotEqualTo(Float value) {
            addCriterion("used_size <>", value, "usedSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeGreaterThan(Float value) {
            addCriterion("used_size >", value, "usedSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeGreaterThanOrEqualTo(Float value) {
            addCriterion("used_size >=", value, "usedSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeLessThan(Float value) {
            addCriterion("used_size <", value, "usedSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeLessThanOrEqualTo(Float value) {
            addCriterion("used_size <=", value, "usedSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeIn(List<Float> values) {
            addCriterion("used_size in", values, "usedSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeNotIn(List<Float> values) {
            addCriterion("used_size not in", values, "usedSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeBetween(Float value1, Float value2) {
            addCriterion("used_size between", value1, value2, "usedSize");
            return (Criteria) this;
        }

        public Criteria andUsedSizeNotBetween(Float value1, Float value2) {
            addCriterion("used_size not between", value1, value2, "usedSize");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}