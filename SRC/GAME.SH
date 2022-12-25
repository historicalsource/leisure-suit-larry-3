;**
;**	game.sh -- all global defines
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 22, 1990
;**


(include  view.sh)
(include	sound.sh)
;** see "scene.sh" for room numbers


(define	VILLAGE_ROOMS 		200 203 210 213 216 220 230 235 240 245 250 253 300 305 310 )
(define	FAT_CITY_ROOMS		360 370 375 380 390 )
(define	CASINO_ROOMS		400 410 415 416 420 460 )
(define	JUNGLE_ROOMS		510 520 523 540 550 )
(define	SIERRA_ROOMS		610 620 630 640 650 )
(define	NO_DEBUG_ROOMS		530 260 420 )


;*********   GLOBAL VARS  *********
(global
	debugging						100
	egoState							101
		(enum								  		;**	egoState values
			NORMAL								;**	These are ok to change rooms:
			DOING_IT

			FALLING
			DRINKING
			GETTING_FIRED
			NAKED
			NAKED_BUT_MODEST
			WEARING_SUIT
			WEARING_TOWEL
			WEARING_SWEATS
			WEARING_GRASS_SKIRT
			WEARING_ZIEGFIELD
			DROWNING_IN_RIVER
			PANTYHOSING
			GOING_TO_BIG_FACE			
			IN_ELEVATOR
			IN_DARK
			OVERHEAD
			BACKSTAGE
			TROPIC
			1000 PREVENT_NEWROOM				;**	no room change allowed

			NEAR_DEATH
			GAMEOVER
			DROWNING								;**	rm260
			SITTING
			LYING_ON_TOWEL
		)
	normalEgoView					102
	gameSeconds						103
	gameMinutes						104
	gameHours						105
	oldTime							106
	secondsInRoom					107
	bgMusicLoops					108
	rgTimer							109
	curTimer							110
		(enum 									;**	currentTimer contains what
			1										;**	rgTimer is presently timing.
			ORCHIDS
		)
	flagArray						111		;**	Access only from SetFlg, ClrFlg,
		(enum										;**	TglFlg and TstFlg Procedures.
			logging			;**	111
			loadDebugNext
			showFrag
			cantSave
			preventAutoSave
			noCursor
			drankRiverWater
			drankSinkWater
			needsShower
			seenPatti
			needsSoap
			scoredTan
			lookedInMirror
			sawAl&BillPoof
			inQA
			forceAtest

			beenIn206		;**	112
			beenIn200		
			beenIn203
			beenIn210
			beenIn216
			beenIn220
			beenIn250
			beenIn350
			sawCredits200
			sawCredits210
			sawCredits213
			sawCredits216
			sawCredits220
			sawCredits250
			sawCredits253
			sawCredits260

			beenIn266		;**	113
			beenIn360
			beenIn395
			beenIn440
			beenIn330
			beenIn510
			beenIn323
			scoredTowel
			scoredLocker
			scoredSweats
			scoredWater							;**	filled the bottle with water
			scoredDuckPoints
			scoredCombination
			scoredSuzi
			passedSRcopyCheck
			tippedMaitreD

			seenCherri		;**	114
			showerRunning
			wetBody
			lockerRippedOff
			hadBambi
			missedBambi
			scoredOrchids
			madeLei
			seenDale
			scoredDale
			pickedPot
			woreGrassSkirt
			sprayedDeodorant
			saidHiToTawni
			needsDeodorant
			usedElevator

			seenJodiStrip	;**	115
			gaveHead
			beenIn480
			tipsIn450
			killedPorky
			tookShortcut
			missedKeycardPoints
			seenBambi
			pantyhoseOff
			braLess
			braLoaded
			scoredBraLess
			scoredBraLoaded
			skippedLogRide
			nextFlag		 
		)
	creditsFinished				112
	lastFlag							117
	egoName							118
	egoNameBuffer					119		;**	6 bytes
	nearPerson						122
		(enum 1									;**	nearPerson values
			man
			woman
			child
			person
		)
	machineSpeed 					123		;**	machineSpeed values:
		(define	pc8088		16)			;**	if < 30 it's a PC
		(define	pcAT			39)			;**	if < 60 it's an AT
		(define	pc386			69)			;**	if > 60 it's a 386
		(define	pc386mhz25	90)			;**	if > 90 it's a 386, 25 mhz
	filthLevel						124
		(enum										;**	corresponds to girl's suitCel
			MOTHER_GOOSE
			CLEAN									
			RISQUE
			DIRTY					
			RAUNCHY
		)
	keyDownHandler					125
	mouseDownHandler				126
	directionHandler				127
	bambooCount						128
	scoreDisplayed					129
	dollars							130
	music								131
	beachState						132
	demo								133
	newspaperState					134		;**		newspaperState gets changed 
		(enum										;**	in these rooms at these times:
			NSnotYet								;**	000 at start of game
			NSshowroom							;**	260 when L gets knife from T
			NSpComing							;**	435 when L learns C wants a farm
			NSpHere								;**	440 when L removes Ziegfield suit
			NSlMissing							;**	483 when L becomes P
		)
	lawyerState						135		;**		lawyerState gets changed 
		(enum										;**	in these rooms at these times:
			LSbusy								;**	000 at start of game
			LSfree								;**	435 when L learns C wants a farm
			LSwaiting4deed						;**	323 at end of Suzi's free chat
			LSdeedReady							;**	320 as L leaves 320 for 220
			LSneeds500							;**	320 as L gets the deed from Roger
			LSdivorce							;**	320 when L gives $500 to Roger
			LSwaiting4divorce					;**	323 during $500 chat (or 324)
			LSdivorceReady						;**	320 as L leaves 320 for 220
			LSdone								;**	320 when L picks up divorce 
		)
	curVendor						136
	oldGameSpeed					137
	prevRoomX						138
	prevRoomY						139
	showroomState					140		;**		showroomState gets changed 
		(enum										;**	in these rooms at these times:
	;**			SRpreShow							;**	000 at start of game
			SRshowIsOn							;**	260 when L gets knife from T
			SRshowDone							;**	430 during C's dance solo
			SRcherriOnPhone					;**	420 as L leaves 420 after 430
			SRknowsAboutDeed					;**	435 when L learns C wants a farm
			SRcherriBackstage					;**	422 when C leaves L at the phone
			SRstageDoorUnlocked				;**	431 during L's big dance number
			SRdone								;**	440 when L removes Ziegfield suit
		)
	textColor						141
	backgroundColor				142
	someObject						143		;**	stores pointer to object for
	egoIsPatti						144		;**		shared communication
	soundFX							145		;**	room 000 would not compile
	stringDelay				  		146
	lockerCombination1			147
	lockerCombination2			148
	lockerCombination3			149
	BenchPressCount				150
	LegCurlsCount					151
	PullupsCount					152
	BarPullCount					153
	egoIsHunk						154
	prevLoop							155
	oldEgoState						156
	nextVar							157

	filthLevelBuffer				170		;**	NOTE: 30 characters long
	expletiveBuffer				200		;**	NOTE: 40 characters long
	expletive						219
	laffer							220
	autoSaveMinutes				221		;**	user's preference
	asMinutes						222		;**	rm0 counters
	asSeconds						223
	dateBuffer						224
	initialsBuffer					229
	noteCounter						232
)


;*********   INVENTORY   (Show Object views match Inventory ID #'s)
(enum
	iDummy						;**	0
	iCreditCard
	iKnife
	iWood
	iGrass
	iSoap							;**	5
	iMoney
	iDeed
	iTowel
	iKeycard										
	iDivorce						;**	10
 	iOrchids
	iKey
	iBottle
	iPanties										
	iPantyhose					;**	15
	iBra
	iDress
	iMarker
	iCoconuts									
	iMarijuana					;**	20
	ivSharpKnife
	ivSculpture
	ivGrassSkirt
	ivOnes										
	ivTips						;**	25
	ivLei
	ivRope
	ivEmptyBottle
	ivWaterBottle								
	ivPattiKey					;**	30
	ivDressShort
	ivDressWet
	ivDressBikini
	ivSculptureModest			;**	34
)



;*********   CONSTANTS   **********
(enum												;**	Standard loops
	facingEast
	facingWest
	facingSouth
	facingNorth
)
(enum												;**	Standard loops
	facingRight
	facingLeft
	facingDown
	facingUp
)
(enum												;**	Door states
	doorClosed
	doorOpening
	doorOpen
	doorClosing
)
(enum												;**	Priorities
	black
	blue
	green
	cyan
	red
	magenta
	brown
	white
	ltBlack
	ltBlue
	ltGreen
	ltCyan
	ltRed
	ltMagenta
	ltBrown
	ltWhite
)
(define	gray			ltBlack)				;**	Synonyms
(define	grey			ltBlack)
;(define	darkGray		ltBlack)				;**	NEXT GAME Synonyms
;(define	darkGrey		ltBlack)
;(define	gray			white)				
;(define	grey			white)
(define	pink			ltRed)
(define	yellow		ltBrown)


(define	cBlack		%0000000000000001) ;**	Bit mapped controls
(define	cBlue			%0000000000000010)
(define	cGreen		%0000000000000100)
(define	cCyan			%0000000000001000)
(define	cRed			%0000000000010000)
(define	cMagenta		%0000000000100000)
(define	cBrown		%0000000001000000)
(define	cWhite		%0000000010000000)
(define	cLtBlack		%0000000100000000)
(define	cLtBlue		%0000001000000000)
(define	cLtGreen		%0000010000000000)
(define	cLtCyan		%0000100000000000)
(define	cLtRed		%0001000000000000)
(define	cLtMagenta	%0010000000000000)
(define	cLtBrown		%0100000000000000)
(define	cLtWhite		%1000000000000000)

(define	cGray			cLtBlack)			;**	Synonyms
(define	cGrey			cLtBlack)
(define	cPink			cLtRed)
(define	cYellow		cLtBrown)
(define	cLtGray		cWhite)
(define	cLtGrey		cWhite)


;Bitmap for the 'signal' property of Props
; MSB xxxxxxxx/xxxPKxUU LSB
; x = don't touch
; P = priority
; K = kill actor
; U = update control

;(define	stopUpdOn		%0000000000000001)	; 1
;(define	startUpdOn		%0000000000000010)	; 2
;(define	updOn				%0000000000000011)	; 3
;(define	notUpd			%0000000000000100)	; 4
;(define	hideActor		%0000000000001000)	; 8
;(define	fixPriOn			%0000000000010000)	; 16
;(define	viewAdded		%0000000000100000)	; 32 view will be added to picture
;(define	forceUpdOn		%0000000001000000)	; 64
;(define	actorHidden		%0000000010000000)	; 128
;(define	staticView		%0000000100000000)	; 256
;(define	anExtra			%0000001000000000)	; 512 can be added to pic if animation speed bad
;(define	blocked			%0000010000000000)	; 1024 tried to move, but couldn't
;(define	fixedLoop		%0000100000000000)	; 2048 loop is fixed
;(define	fixedCel			%0001000000000000)	; 4096 cel fixed
;(define	ignrHrz			%0010000000000000)	; 8192 can ignore horizon
;(define	ignrAct			%0100000000000000)	;16384 can ignore other actors
;(define	delObj			%1000000000000000)	;32768


(define	RedrawCast			Animate (cast elements) FALSE)
(define	blackPicture		099)
(define	LIMBO					-1)				;**	for inventory Move Object to
(define	RELEASE				-1)				;**	for setLoop, setPri
(define	FOREVER				-1)				;**	for music
(define	readyToStartOver	-1)				;**	for scripts to loop back to 0
(define	delay					= seconds)		;**	use in scripts
(define	LastCel				255)				;**	for setCel
(define	ctForward			1)					;**	for CycleTo stuff
(define	ctReverse	  		-1)
(define	HIGH					#at -1  10)		;**	for consistent Print at's
(define	LOW					#at -1 144)
(define	BIG		   		#at 10   5 #width 290)
(define	WIDE					#at 10  -1 #width 290)
(define	FALLING_STEP		1 8)
(define	SHAKE_UP_DOWN		1)					;**	shake screen constants
(define	SHAKE_LEFT_RIGHT	2)
(define	SHAKE_DIAGONALLY	3)
(define	NO_CUE				0)					;**	don't cue
(define	observeControls	observeControl);**	lazy shit
(define	ignoreControls		ignoreControl)
(define	FLOAT					illegalBits 0)	;**	in memory of Carlos
(define	ERROR					-1)

(define	AL						991)				;**	Al's head
(define	KEYBOARD				992)				;**	used only when canInput TRUE
(define	BIRD					995)				;**	a personal statement
(define	NO_CURSOR			666)				;**	thanks, Scott


;***********   FONTS   ***********
(define	HELVETICA_12			0)
(define	SERIF_12					1)
(define	SANS_SERIF_12			2)
(define	SANS_SERIF_10			3)
(define	SERIF_9					4)
(define	BIG_FAT_18 				7)
;(define	LONDON_14				8)
(define	MASH_18					9)
;(define	2_AGAIN?					69)
;(define	DATA_12					200)
;(define	SQ3_12					300)
;(define	SANS_SERIF_SC_9		600)
;(define	600_MIRRORED			601)
;(define	ALMOST_600_AGAIN		602)
;(define	SECRET_MESSAGE			603)
;(define	SANS_SERIF_	9			604)
(define	SANS_SERIF_8			999)
;(define	SYSFONT					0)
;(define	USERFONT					1)


;***** External procedures	******
(extern	NormalEgo			0	 1)
(extern	NormalActor			0	 2)
(extern	HandsOff				0	 3)
(extern	HandsOn				0	 4)
(extern	cls					0	 5)
(extern	Ok						0	 6)
(extern	ItIs					0	 7)
(extern	YouAre				0	 8)
(extern	NotNow				0	 9)
(extern	NotClose				0	10)
(extern	AlreadyTook			0	11)
(extern	DontHave				0	12)
(extern	Notify				0	13)
(extern	HaveMem				0	14)
(extern	AddActorToPic		0	15)
(extern	SetRgTimer			0	16)
(extern	LogIt					0	17)
(extern	LameResponse		0	18)
(extern	SetFlg				0	19)
(extern	ClrFlg				0	20)
(extern	TglFlg				0	21)
(extern	TstFlg				0	22)
(extern	InRoom				0	23)
(extern	PutInRoom			0	24)
(extern	PrintDelay			0	25)
;(extern	LoadMany				0	26)

(extern	TglSound			MENU	 1)
(extern	ShowState		  21	 1)


;*****   ROOM AND REGIONS   *******
;(define	rmMENU						010)	;**	output is script.997
;(define	MENU							997)
(define	DEBUG							020)
(define	SHOW_STATE					021)
(define	QA								022)
(define	INVENTORY_TEXT				030)
(define	DYING							040)
(define	regFalling					041)
(define	LEI 	 						042)
(define	CARVING						043)
(define	WEAVING						044)
(define	LOCKER						045)
(define	LOCKER_TEXT					046)
	(define LAST_DOCO_PAGE		24)
(define	DOOR							050)
(define	AUTODOOR						051)
(define	RNDCYCLE						052)
(define	locGirls						070)
(define	regBigFace					071)
(define	locClerkL					080)
(define	locClerkP					081)
(define	BOSS_KEY						090)
(define	SPEED_TEST					290)
(define	COPY_CHECK					140)
(define	locVillage					299)
(define	locFatCity					399)
(define	regCasinoHallway			417)
(define	regLovemaking				489)
(define	locCasino					499)
(define	locJungle					599)
(define	locSierra					699)

