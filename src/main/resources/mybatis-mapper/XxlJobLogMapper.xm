<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.xxl.job.admin.dao.XxlJobLogDao">
	
	<resultMap id="XxlJobLog" type="com.xxl.job.admin.core.model.XxlJobLog" >
		<result column="id" property="id" />

		<result column="job_group" property="jobGroup" />
		<result column="job_id" property="jobId" />

		<result column="executor_address" property="executorAddress" />
		<result column="executor_handler" property="executorHandler" />
	    <result column="executor_param" property="executorParam" />
		<result column="executor_sharding_param" property="executorShardingParam" />
		<result column="executor_fail_retry_count" property="executorFailRetryCount" />
	    
	    <result column="trigger_time" property="triggerTime" />
	    <result column="trigger_code" property="triggerCode" />
	    <result column="trigger_msg" property="triggerMsg" />
	    
	    <result column="handle_time" property="handleTime" />
	    <result column="handle_code" property="handleCode" />
	    <result column="handle_msg" property="handleMsg" />

		<result column="alarm_status" property="alarmStatus" />
	</resultMap>

	<sql id="Base_Column_List">
		t.id,
		t.job_group,
		t.job_id,
		t.executor_address,
		t.executor_handler,
		t.executor_param,
		t.executor_sharding_param,
		t.executor_fail_retry_count,
		t.trigger_time,
		t.trigger_code,
		t.trigger_msg,
		t.handle_time,
		t.handle_code,
		t.handle_msg,
		t.alarm_status
	</sql>
	
	<select id="pageList" resultMap="XxlJobLog">
		SELECT <include refid="Base_Column_List" />
		FROM xxl_job_log AS t
		<trim prefix="WHERE" prefixOverrides="AND | OR" >
			<if test="jobId==0 and jobGroup gt 0">
				AND t.job_group = #{jobGroup}
			</if>
			<if test="jobId gt 0">
				AND t.job_id = #{jobId}
			</if>
			<if test="triggerTimeStart != null">
				AND t.trigger_time <![CDATA[ >= ]]> #{triggerTimeStart}
			</if>
			<if test="triggerTimeEnd != null">
				AND t.trigger_time <![CDATA[ <= ]]> #{triggerTimeEnd}
			</if>
			<if test="logStatus == 1" >
				AND t.handle_code = 200
			</if>
			<if test="logStatus == 2" >
				AND (
					t.trigger_code NOT IN (0, 200) OR
					t.handle_code NOT IN (0, 200)
				)
			</if>
			<if test="logStatus == 3" >
				AND t.trigger_code = 200
				AND t.handle_code = 0
			</if>
		</trim>
		ORDER BY t.trigger_time DESC
		LIMIT #{offset}, #{pagesize}
	</select>
	
	<select id="pageListCount" resultType="int">
		SELECT count(1)
		FROM xxl_job_log AS t
		<trim prefix="WHERE" prefixOverrides="AND | OR" >
			<if test="jobId==0 and jobGroup gt 0">
				AND t.job_group = #{jobGroup}
			</if>
			<if test="jobId gt 0">
				AND t.job_id = #{jobId}
			</if>
			<if test="triggerTimeStart != null">
				AND t.trigger_time <![CDATA[ >= ]]> #{triggerTimeStart}
			</if>
			<if test="triggerTimeEnd != null">
				AND t.trigger_time <![CDATA[ <= ]]> #{triggerTimeEnd}
			</if>
			<if test="logStatus == 1" >
				AND t.handle_code = 200
			</if>
			<if test="logStatus == 2" >
				AND (
					t.trigger_code NOT IN (0, 200) OR
					t.handle_code NOT IN (0, 200)
				)
			</if>
			<if test="logStatus == 3" >
				AND t.trigger_code = 200
				AND t.handle_code = 0
			</if>
		</trim>
	</select>
	
	<select id="load" parameterType="java.lang.Long" resultMap="XxlJobLog">
		SELECT <include refid="Base_Column_List" />
		FROM xxl_job_log AS t
		WHERE t.id = #{id}
	</select>

	
	<insert id="save" parameterType="com.xxl.job.admin.core.model.XxlJobLog" useGeneratedKeys="true" keyProperty="id" >
		INSERT INTO xxl_job_log (
			`job_group`,
			`job_id`,
			`trigger_time`,
			`trigger_code`,
			`handle_code`
		) VALUES (
			#{jobGroup},
			#{jobId},
			#{triggerTime},
			#{triggerCode},
			#{handleCode}
		);
		<!--<selectKey resultType="java.lang.Integer" order="AFTER" keyProperty="id">
			SELECT LAST_INSERT_ID() 
		</selectKey>-->
	</insert>

	<update id="updateTriggerInfo" >
		UPDATE xxl_job_log
		SET
			`trigger_time`= #{triggerTime},
			`trigger_code`= #{triggerCode},
			`trigger_msg`= #{triggerMsg},
			`executor_address`= #{executorAddress},
			`executor_handler`=#{executorHandler},
			`executor_param`= #{executorParam},
			`executor_sharding_param`= #{executorShardingParam},
			`executor_fail_retry_count`= #{executorFailRetryCount}
		WHERE `id`= #{id}
	</update>

	<update id="updateHandleInfo">
		UPDATE xxl_job_log
		SET 
			`handle_time`= #{handleTime}, 
			`handle_code`= #{handleCode},
			`handle_msg`= #{handleMsg}
		WHERE `id`= #{id}
	</update>
	
	<delete id="delete" >
		delete from xxl_job_log
		WHERE job_id = #{jobId}
	</delete>

    <!--<select id="triggerCountByDay" resultType="java.util.Map" >
		SELECT
			DATE_FORMAT(trigger_time,'%Y-%m-%d') triggerDay,
			COUNT(handle_code) triggerDayCount,
			SUM(CASE WHEN (trigger_code in (0, 200) and handle_code = 0) then 1 else 0 end) as triggerDayCountRunning,
			SUM(CASE WHEN handle_code = 200 then 1 else 0 end) as triggerDayCountSuc
		FROM xxl_job_log
		WHERE trigger_time BETWEEN #{from} and #{to}
		GROUP BY triggerDay
		ORDER BY triggerDay
    </select>-->

    <select id="findLogReport" resultType="java.util.Map" >
		SELECT
			COUNT(handle_code) triggerDayCount,
			SUM(CASE WHEN (trigger_code in (0, 200) and handle_code = 0) then 1 else 0 end) as triggerDayCountRunning,
			SUM(CASE WHEN handle_code = 200 then 1 else 0 end) as triggerDayCountSuc
		FROM xxl_job_log
		WHERE trigger_time BETWEEN #{from} and #{to}
    </select>

	<select id="findClearLogIds" resultType="long" >
		SELECT id FROM xxl_job_log
		<trim prefix="WHERE" prefixOverrides="AND | OR" >
			<if test="jobGroup gt 0">
				AND job_group = #{jobGroup}
			</if>
			<if test="jobId gt 0">
				AND job_id = #{jobId}
			</if>
			<if test="clearBeforeTime != null">
				AND trigger_time <![CDATA[ <= ]]> #{clearBeforeTime}
			</if>
			<if test="clearBeforeNum gt 0">
				AND id NOT in(
				SELECT id FROM(
				SELECT id FROM xxl_job_log AS t
				<trim prefix="WHERE" prefixOverrides="AND | OR" >
					<if test="jobGroup gt 0">
						AND t.job_group = #{jobGroup}
					</if>
					<if test="jobId gt 0">
						AND t.job_id = #{jobId}
					</if>
				</trim>
				ORDER BY t.trigger_time desc
				LIMIT 0, #{clearBeforeNum}
				) t1
				)
			</if>
		</trim>
		order by id asc
		LIMIT #{pagesize}
	</select>

	<delete id="clearLog" >
		delete from xxl_job_log
		WHERE id in
		<foreach collection="logIds" item="item" open="(" close=")" separator="," >
			#{item}
		</foreach>
	</delete>

	<select id="findFailJobLogIds" resultType="long" >
		SELECT id FROM `xxl_job_log`
		WHERE !(
			(trigger_code in (0, 200) and handle_code = 0)
			OR
			(handle_code = 200)
		)
		AND `alarm_status` = 0
		ORDER BY id ASC
		LIMIT #{pagesize}
	</select>

	<update id="updateAlarmStatus" >
		UPDATE xxl_job_log
		SET
			`alarm_status` = #{newAlarmStatus}
		WHERE `id`= #{logId} AND `alarm_status` = #{oldAlarmStatus}
	</update>

	<select id="findLostJobIds" resultType="long" >
		SELECT
			t.id
		FROM
			xxl_job_log t
			LEFT JOIN xxl_job_registry t2 ON t.executor_address = t2.registry_value
		WHERE
			t.trigger_code = 200
				AND t.handle_code = 0
				AND t.trigger_time <![CDATA[ <= ]]> #{losedTime}
				AND t2.id IS NULL;
	</select>
	<!--
	SELECT t.id
	FROM xxl_job_log AS t
	WHERE t.trigger_code = 200
		and t.handle_code = 0
		and t.trigger_time <![CDATA[ <= ]]> #{losedTime}
		and t.executor_address not in (
			SELECT t2.registry_value
			FROM xxl_job_registry AS t2
		)
	-->

</mapper>