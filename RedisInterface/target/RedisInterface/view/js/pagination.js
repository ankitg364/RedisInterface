/**
 * Created by asad on 26/8/14.
 */
var prevIndex = {"0": "0"};
var nextIndex = [];
var currIndex = 0;

var prevIndexString = {"0": "0"};
var nextIndexString = [];
var currIndexString = 0;

var prevIndexList = {"0": "0"};
var nextIndexList = [];
var currIndexList = 0;

var prevIndexZSet = {"0": "0"};
var nextIndexZSet = [];
var currIndexZSet = 0;

var prevIndexSet = {"0": "0"};
var nextIndexSet = [];
var currIndexSet = 0;

var prevIndexHash = {"0": "0"};
var nextIndexHash = [];
var currIndexHash = 0;

function resetIndexPointers()
{
    prevIndex = {"0": "0"};
    nextIndex = [];
    currIndex = 0;

    prevIndexString = {"0": "0"};
    nextIndexString = [];
    currIndexString = 0;

    prevIndexList = {"0": "0"};
    nextIndexList = [];
    currIndexList = 0;

    prevIndexZSet = {"0": "0"};
    nextIndexZSet = [];
    currIndexZSet = 0;

    prevIndexSet = {"0": "0"};
    nextIndexSet = [];
    currIndexSet = 0;

    prevIndexHash = {"0": "0"};
    nextIndexHash = [];
    currIndexHash = 0;
}
