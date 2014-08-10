<#-- the JS to make the links actually do something is in html/global.js -->
<#-- depends on free variables:
     resultCount
     pageSize
     offset
-->
<#macro resultRangeText>
  <#if resultCount < pageSize>
    Results 1 - ${resultCount}
  <#else>
    Results ${offset+1} -
    <#if (offset+pageSize) < resultCount>
      ${offset+pageSize}
    <#else>
      ${resultCount}
    </#if>
    of ${resultCount}
  </#if>
</#macro>

<#-- depends on free variables:
     resultCount
     pageSize
     offset
-->
<#macro pageNumberLinks>
  <#-- only show previous link if not first page -->
  <#if 0 < offset>
       <a href="#" class="pageControl" rel="${(offset-pageSize)?c}"><span class="page_arrow">&lsaquo;</span> Previous</a>
       <span class="pipebreak">|</span>
  </#if>

  <#assign pageCount = (resultCount/pageSize)?ceiling />
  <#if 1 < pageCount>
       <#assign currentPage = (offset/pageSize)?floor+1 />
       <@pages 1..pageCount currentPage />
  </#if>

  <#-- only show next link if not last page -->
  <#if (offset + pageSize) < resultCount>
       <span class="pipebreak">|</span>
       <a href="#" class="pageControl" rel="${(offset+pageSize)?c}">Next <span class="page_arrow">&rsaquo;</span></a>
  </#if>
</#macro>

<#--
Code grabbed from
http://stackoverflow.com/questions/6391668/freemarker-pagination-or-just-general-algorithm-for-clicking-through-pages

modified to use the <a> tags I needed
-->
<#function max x y>
    <#if (x<y)><#return y><#else><#return x></#if>
</#function>
<#function min x y>
    <#if (x<y)><#return x><#else><#return y></#if>
</#function>
<#macro pages totalPages p>
    <#assign size = totalPages?size>
    <#if (p<=4)> <#-- p among first 5 pages -->
        <#assign interval = 1..(min(5,size))>
    <#elseif ((size-p)<4)> <#-- p among last 5 pages -->
        <#assign interval = (max(1,(size-4)))..size >
    <#else>
        <#assign interval = (p-2)..(p+2)>
    </#if>
    <#if !(interval?seq_contains(1))>
     <a href="#" class="pageControl" rel="0">1</a> ... <#rt>
    </#if>
    <#list interval as page>
        <#if page=p>
         <span style="font-weight: bold;">
        </#if>
	<a href="#" class="pageControl" rel="${((page-1)*pageSize)?c}">${page}</a> <#t>
        <#if page=p>
         </span>
        </#if>
    </#list>
    <#if !(interval?seq_contains(size))>
     ... <a href="#" class="pageControl" rel="${((resultCount/pageSize)?floor*pageSize)?c}">${size}</a><#lt>
    </#if>
</#macro>
