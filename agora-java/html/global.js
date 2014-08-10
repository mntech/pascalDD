/*
$('.non_premium').click(function() {
	$('.non_premium').toggleClass('non_premium_highlight');
	$('#premium_popin').slideToggle('fast', function() {
    	// Animation complete.
  	});
});
*/
// for the page number links
$("a.pageControl").click(function(ev) {
    $("input[name='offset']").attr("value", ev.currentTarget.rel);
    $(".pagedForm").submit();
});
// so it doesn't jump back to the top before the next page
$("a.pageControl").attr("href", "javascript:void(0)");

// column ordering
$('th.sortable').click(function() {
    var $header = $(this);
    clearForm();

    var $sortColumn = $('#sortColumn');
    var $sortOrder = $('#sortOrder');

    var currentSortColumn = $sortColumn.val();
    var currentSortOrder = $sortOrder.val();

    var newSortColumn = $header.attr('data-column');
    var newSortOrder = '';
    if (currentSortColumn === newSortColumn) {
        if (currentSortOrder === 'asc') {
            newSortOrder = 'desc';
        } else {
            newSortOrder = 'asc';
        }
    } else {
        // New column
	if (newSortColumn === 'company_name') {
            newSortOrder = 'asc';
	} else {
            newSortOrder = 'desc';
	}
    }

    // We have the new sort column and order, set them in the 
    // form and submit it.
    $sortColumn.val(newSortColumn);
    $sortOrder.val(newSortOrder);

    // This ties the code to the TA form, but we could generalize 
    // it to sort on other forms.
    $('#top-advertisers-form').submit();
});
// Set the sort class on the proper header to get the little arrow
var sortColumn = $('#sortColumn').val();
var sortOrder = $('#sortOrder').val();
var $header = $('.sortable[data-column=' + sortColumn + ']');
if (sortOrder === 'asc') {
    $header.addClass('headerSortDown');
} else {
    $header.addClass('headerSortUp');
}

$('#useDomainSet').click(function() {
	if($('#useDomainSet').is(":checked")) {
		$("#domain").attr("disabled", "disabled");
		$("#fake_hider").css("display", "inline");
	} else {
		$("#domain").removeAttr("disabled");
		$("#fake_hider").css("display", "none");
	}
	
});

// Code for domain lists expander gizmo thingy.
// Implemented on topAdvertisers page.
// When the user clicks a table row, the corresponding 
// domain list is expanded based on the number of elements 
// in the list.
// Note: animating to 100% should have worked here but there's 
// a bug in the version of jQuery we're on...
$('.advertisers tbody tr').each(function() {
    var $this = $(this);
    var $list = $this.find('.domain_list div');
    
    var h = $list.find('tr').first().outerHeight();
    var n = $list.find('tr').length;
    
    // Set the height correctly based on the height of each 
    // list item. Do this here instead of in CSS so we 
    // don't end up choking when somebody changes the text 
    // style of the list items. We only do this for lists 
    // with >3 items since others don't need to be expanded.
    if (n > 3) {
        $list.css({'max-height': 3*h+'px'});
        $list.addClass('collapsed');
    }

    // Keep track of all this crap on the item itself for 
    // simplicity. We use these to decide what to do when 
    // the user clicks on a row. This is kind of ugly but 
    // it's quick and easy, so it's happening.
    $list.data('itemHeight', h);
    $list.data('childCount', n);
    $list.data('stateExpanded', false);

    // Handler for when the user clicks a row. First we check 
    // the current status. If we're not expanded, we expand, 
    // otherwise we contract.
    $this
    .click(function() {
        var h = $list.data('itemHeight');
        var n = $list.data('childCount');
        var ex = $list.data('stateExpanded');

        // If there are >=3 items in the list, don't do 
        // anything.
        if (n <= 3) {
            return;
        }

        // Take the correct action based on whether the table 
        // is currently expanded or not.
        if (ex) {
            $list.animate({'max-height': 3*h+'px'}, 100);
            $list.addClass('collapsed');
        } else {
            $list.animate({'max-height': n*h+'px'}, 100);
            $list.removeClass('collapsed');
        }

        $list.data('stateExpanded', ! ex);
    });
});

// Code for date-range selectors. We just look at the start 
// date and hide options for the end date selector that are 
// before it.
$('.date-range').each(function() {
    // Grab the whole thing
    var $this = $(this);
    var $selectors = $this.find('select');

    // Get the form fields for start and end
    var $start_mo = $("select[name='startMonth']");
    var $start_yr = $("select[name='startYear']");
    var $end_mo = $("select[name='endMonth']");
    var $end_yr = $("select[name='endYear']");

    $this.data('start-month-field', $start_mo);
    $this.data('start-year-field', $start_yr);
    $this.data('end-month-field', $end_mo);
    $this.data('end-year-field', $end_yr);

    // Function to handle updating the end year choices 
    // based on what is selected for the start.
    $this.data('update', function() {
        var $this = $(this);

        // Update the year.
        var start_yr = parseInt($this.data('start-year-field').val());
        var $end_yr = $this.data('end-year-field');
        var end_yr = parseInt($end_yr.val());

        // Update the end year if it is before the 
        // start year. Make them the same in this case.
        if (end_yr < start_yr) {
            $end_yr.val(start_yr);
        }

        // Disable end year options that are no longer 
        // reasonable given the new start year.
        $end_yr.find('option').each(function() {
            var $option = $(this);
            var val = parseInt($option.val());
            if (val < start_yr) {
                $option.attr('disabled', 'true');
            } else {
                $option.removeAttr('disabled');
            }
        });

        // Update the month.
        var start_mo = parseInt($this.data('start-month-field').val());
        var $end_mo = $this.data('end-month-field');
        var end_mo = parseInt($end_mo.val());

        // Updating the month is analogous to the year, but 
        // we only care about this if the year are the same.
        if (end_mo < start_mo && start_yr === end_yr) {
            $end_mo.val(start_mo);
        }

        // Disable month options if necessary. We re-enable 
        // them no matter what.
        $end_mo.find('option').each(function() {
            var $option = $(this);
            var val = parseInt($option.val());
            if (val < start_mo && start_yr === end_yr) {
                $option.attr('disabled', 'true');
            } else {
                $option.removeAttr('disabled');
            }
        });
    });

    // We want to call the update function each time the 
    // selection changes.
    $selectors.change(function() {
        $this.data('update').apply($this);
    });
});
    // There are 2 cases, one of which has 2 sub-cases.
    // 1) Start uses latest year, in which case only months 
    //    >= the start month are valid for the end month and 
    //    only the latest year is valid
    // 2) General case, only years >= the start year are valid 
    //    for the end year, but there are two sub-cases, 
    //    depending on the end selection:
    //      a) If the start and end years are equal, then only 
    //         months >= start month are valid end months
    //      b) General case, all months are valid end months

/*
 * Code for the range bars.
 */
$(function() {
    $('.rangebar').each(function() {
        var $bar = $(this);

        var start = parseInt($bar.data('start'));
        var end   = parseInt($bar.data('end'));

        var barWidth = (end - start) + '%';
        var barShift = start + '%';

        $active = $('<div>')
        .addClass('rangebar-active')
        .width(barWidth)
        .css('margin-left', barShift);

        $bar.append($active);
    });
});

