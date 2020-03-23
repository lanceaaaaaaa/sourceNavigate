function moveNoteLocation(moveNote,moveX,moveY) {
    moveNote.positions(function( node, i ){
        return {
            x: moveX,
            y: moveY
        };
    });
}

function toSelectNote(rootId,queryId) {
    var rootNote = cy.getElementById(rootId);
    var queryNote = cy.getElementById(queryId);
    var xDiff = queryNote.position().x - rootNote.position().x;
    var yDiff = queryNote.position().y - rootNote.position().y;
    var allNodes = cy.nodes();
    for(var j=0;j<allNodes.length;j++){
        moveNoteLocation(allNodes[j],allNodes[j].position().x-xDiff,allNodes[j].position().y-yDiff);
    }
}

/**
 * 图谱缩放
 * type == 1  放大
 * type == 2  缩小
 */
function maoScale(type){
    var rate =  0.2;
    var scale = cy.zoom();
    scale = 1.6;
    console.log("scale:"+scale)
    cy.zoom({
        level: scale, // the zoom level
    });
}