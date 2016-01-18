# LeTwist
Letter Twisting Fun

A word game that tests your ability to form words from given letters.

Tim Nguyen (nguy1877@umn.edu)  
2016

## Get
Download the source code.  
The class that drives the game is found in Driver.java.  
Java is required to run this game.

## How to Start Game
Compile the Java files and start the game:  

    % javac Driver.java  
    % java Driver

## Instructions
Make valid words from the letters provided.  
Score points for each word found.  
Advance to the next round by finding one of the max length words.  
You have 2 minutes per round to get as many words as you can!  

Possible Actions to Enter:  
1. A word that can be made out of the letters  
2. "next round" - to go to the next round  
3. "exit game" - to exit the game  
4. "shuffle me" - to shuffle your letters  
5. "help me" - to show this help dialogue  

## Words
The English Open World List (EOWL) was used for all possible words that could
be formed by the letters. It contains 128,985 words, with the largest words
possible being 10 letters long.

More Info: http://dreamsteep.com/projects/the-english-open-word-list.html

## Example
Start Screen

    LeTwist

    Enter "help me" for help

    LeTwist: Letter Twisting Fun
    By Tim Nguyen

    Instructions:
    Make valid words from the letters provided.
    Score points for each word found.
    Advance to next round by finding one of the max length words.
    You have 2 minutes per round to get as many words as you can!

    Possible Actions to Enter:
    A word that can be made out of the letters
    "next round" - to go to the next round
    "exit game" - to exit the game
    "shuffle me" - to shuffle your letters
    "help me" - to show this help dialogue

    Press Enter to begin

Mid-Round Screen

    LeTwist

    Enter "help me" for help

    ---	    ANT	    ARM	    ---	    ---	    MAN
    MAR	    ---	    MAT	    ---	    ---	    ---
    ---	    ---	    ---	    ---	    RAT	    ---
    SAN	    ---	    SAT	    TAM	    TAN	    TAR
    ---	    ----	----	ANTS	ARMS	----
    ----	----	----	----	MANS	----
    MARS	MART	----	MAST	MATS	----
    ----	----	----	----	----	RANT
    ----	RATS	----	STAR	----	----
    TANS	----	----	TARS	----	----
    -----	-----	-----	-----	-----	-----
    -----	MARTS	-----	-----	RANTS	-----
    -----	-----	SMART	-----	-----	-----
    -----	-----	------	------	------	------
    ARTSMAN	-------

    n r a t a m s

    Score: 27150    Time: 00:24    Round: 4
    Nice

    artsman

End-Round Screen

    LeTwist

    Enter "help me" for help

    cit	    die	    DIT	    ers	    est	    ICE
    IDE	    ids	    IRE	    its	    REC	    RED
    res	    RET	    rid	    rit	    SEC	    SED
    sei	    SET	    sic	    sir	    SIT	    ted
    tes	    tic	    TID	    tie	    tis	    cedi
    cert	CIRE	cist	CITE	cits	CRED
    crit	DICE	dict	dies	diet	dire
    dirt	disc	dite	DITS	edit	eric
    erst	ICED	icer	ICES	IDES	IRES
    RECS	REDS	reis	REST	RETS	RICE
    ride	rids	rise	rite	rits	sect
    seir	sice	side	SIRE	SITE	sted
    stir	teds	TICE	tics	TIDE	TIDS
    tied	tier	ties	tire	trie	cedis
    certs	cider	CIRES	cited	citer	CITES
    crest	CRIED	CRIES	crise	crits	deist
    dicer	DICES	diets	dirts	drest	DRIES
    edict	edits	erics	icers	recti	reist
    resit	riced	RICES	rides	rites	seric
    sider	SIRED	SITED	stied	stire	strid
    tices	TIDES	tiers	tired	tires	TRICE
    TRIED	TRIES	ciders	cisted	citers	credit
    dicers	direct	direst	driest	edicts	scried
    steric	stride	triced	TRICES	credits	directs

    s r c e d i t

    Score: 34970	Time: 00:00	Round: 5
    Game over

## Notes

Contact Tim with any questions, comments, or suggestions at nguy1877@umn.edu
