<#--
    Markup for the search/filter form on the Missing Advertisers page.
-->

<#include "dataUtil.ftl"/>
<form action="topAdvertisers.m" method="post" id="top-advertisers-form" class="pagedForm">
    <div class="row">
        <fieldset class="span span6 date-range">
            <label>Search Dates</label>
            <select id="startMonth" name="startMonth" style="margin-right: 4px;">
                <option value="1">Jan</option>
                <option value="2">Feb</option>
                <option value="3">Mar</option>
                <option value="4">Apr</option>
                <option value="5">May</option>
                <option value="6">Jun</option>
                <option value="7">Jul</option>
                <option value="8">Aug</option>
                <option value="9">Sept</option>
                <option value="10">Oct</option>
                <option value="11">Nov</option>
                <option value="12">Dec</option>
            </select>
            <select id="startYear" name="startYear">
				  <@date_options/>
            </select>
            through
            <select id="endMonth" name="endMonth" style="margin-right: 4px;">
                <option value="1">Jan</option>
                <option value="2">Feb</option>
                <option value="3">Mar</option>
                <option value="4">Apr</option>
                <option value="5">May</option>
                <option value="6">Jun</option>
                <option value="7">Jul</option>
                <option value="8">Aug</option>
                <option value="9">Sept</option>
                <option value="10">Oct</option>
                <option value="11">Nov</option>
                <option value="12">Dec</option>
            </select>
            <select id="endYear" name="endYear">
			    <@date_options/>
            </select>
            <#include "defaultSearchPeriod.ftl"/>
            <@defaultSearchPeriod/>
            <script type="text/javascript">
            function resetDates() {
            var sm = $("select[name='startMonth']");
            sm.val(sm.get(0).options[${defaultStartMonthIdx}].value);
            $("select[name='startYear']").val("${defaultStartYear}");
            var em = $("select[name='endMonth']");
            em.val(em.get(0).options[${defaultEndMonthIdx}].value);
            $("select[name='endYear']").val("${defaultEndYear}");
            <#if startYear??>
            $("select[name='startMonth']").val(${startMonth?c});
            $("select[name='startYear']").val(${startYear?c});
            $("select[name='endMonth']").val(${endMonth?c});
            $("select[name='endYear']").val(${endYear?c});
            </#if>
            }
            function clearForm() {
            resetDates();
            $("select[name='domain1']").val("${domain1!''}");
            $("select[name='domain2']").val("${domain2!''}");
            $("select[name='domain3']").val("${domain3!''}");
            }
            $(clearForm);
            </script>

        </fieldset>
    </div> <!-- row -->
    <div class="row">
        <fieldset class="span span6">
            <label>Domain</label>
            <select class="domains" name="domain" size="1">
                <option value="">--</option>
                <#list domainList as d>
                <option value="${d.id}">${d.title}</option>
                </#list>
            </select>
        </fieldset>
    </div> <!-- row -->
    <div class="row">
        <div class="span span6">
            <button type="submit" class="big_submit">Generate Report</button>
            <button type="button" onclick="clearForm();" style="width:112px">Reset Filters</button>
        </div>
    </div> <!-- row -->
    <div class="clear">&nbsp;</div>
</form>
