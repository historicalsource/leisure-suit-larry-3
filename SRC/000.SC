;**
;**	Logics for room 0 -- "'cause Al used to be a room zero-kind of guy!"
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 22, 1990
;**
;**	First Code:		March 28, 1989
;**


(script#				0)


(include	"menu.sh")

(define	vAutoSave					052)

(define	sScored						001)


(procedure
	NormalEgo
	NormalActor
	HandsOff
	HandsOn
	cls
	Ok
	ItIs
	YouAre
	NotNow
	NotClose
	AlreadyTook
	DontHave
	Notify
	HaveMem
	AddActorToPic
	SetRgTimer
	LogIt
	LameResponse
	SetFlg
	ClrFlg
	TglFlg
	TstFlg
	InRoom
	PutInRoom
	PrintDelay
;**		LoadMany
)

(public
	LL3					0
	NormalEgo			1
	NormalActor			2
	HandsOff				3
	HandsOn				4
	cls					5
	Ok						6
	ItIs					7
	YouAre				8
	NotNow				9
	NotClose				10
	AlreadyTook			11
	DontHave				12
	Notify				13
	HaveMem				14
	AddActorToPic		15
	SetRgTimer			16
	LogIt					17
	LameResponse		18
	SetFlg				19
	ClrFlg				20
	TglFlg				21
	TstFlg				22
	InRoom				23
	PutInRoom			24
	PrintDelay			25
;**		LoadMany				26
)


(instance LL3 of Game
	(properties
		name				"LSL3"
	)

	(method (init &tmp testRoom)
		((= systemWindow theWindow)
			color 		(= textColor			blue),
			back			(= backgroundColor	ltWhite),
		)

		(super init)

;**			(= demo TRUE)
		;**	Comment out these lines (and the ones below) before shipping:

;**			(= debugging TRUE)
;**			(self setSpeed 2)
;**			(SetFlg logging)
;**			(SetFlg inQA)
;**			(= autoSaveMinutes	5)


		(= laffer			"\"My name is Larry; Larry Laffer.\"")
		(= version 			{x.yyy})
		(= volume			15)
		(DoSound ChangeVolume volume)
		(StatusLine code statusCode)
		(TheMenuBar 		init)
		(scoreSound
			owner				self,
			init
		)
		((= music theMusic)
			owner				self,
			init
		)
		((= soundFX theSoundFX)
			owner				self,
			init
		)
		(User
			alterEgo			(= ego egoObj),
			blocks			FALSE,
		;	echo				SPACEBAR,
		;	x					-1,
			y 					150,
		)
		(if (HaveMouse)						;**	put hand in lower right corner
			(theGame setCursor normalCursor TRUE)
		else
			(theGame setCursor normalCursor TRUE 304 174)
		)

		;**	Load up everything we're gonna use anyway!
		(Load FONT (= bigFont 	HELVETICA_12))	;**	0
		(Load FONT (= userFont	SERIF_12))		;**	1
		(Load FONT (= smallFont	SERIF_9))		;**	4
		(Load FONT					SANS_SERIF_8)	;**	999

		(Load CURSOR normalCursor)
		(Load CURSOR waitCursor)
		(Load CURSOR NO_CURSOR)
		(Load CURSOR KEYBOARD)

		(Inventory
			add				Dummy
								CreditCard
								Knife
								Wood
								Grass
								Soap
								Money
								Deed
								Towel
								Keycard
								Divorce
								Orchids
								Key
								Bottle
								Panties
								Pantyhose
								Bra
								Dress
								Marker
								Coconuts
								Marijuana
		)


;**	Comment out the debugging lines before shipping:
		(cond
			((GameIsRestarting)
				(= testRoom 290)
			)
;**				(demo
;**					(= creditsFinished $FFFF)	
;**					(= testRoom 120)
;**				)

;**				(debugging
;**					(= stringDelay			33)
;**					(= filthLevel			RAUNCHY)
;**					(= autoSaveMinutes	5)
;**	 				(= creditsFinished	0)
;**					(= stringDelay			20)
;**					(= normalEgoView		vLarry)
;**					(= egoName				(Format @egoNameBuffer "Larry"))
;**					(= expletive (Format @expletiveBuffer "%s" {Son of a bitch!}))
;**					(if (> (DoSound NumVoices) 3)
;**						(= bgMusicLoops	FOREVER)
;**					else
;**						(= bgMusicLoops	1)
;**					)
;**					(= testRoom (GetNumber {Starting Room}))
;**					(if (<= testRoom 0)
;**						(= testRoom 120)
;**					else
;**						(TheMenuBar draw)
;**						(StatusLine enable)
;**					)
;**				)

			(else
				(= testRoom 120)
			)
		)
		(self newRoom testRoom)
	)


	(method (replay)
		(TheMenuBar	draw)
		(StatusLine	enable)
		(SetMenu	soundI #text (if (DoSound SoundOn) {Turn Off} else {Turn On}))
		(super replay)
	)


	(method (newRoom n style)
;**			(if (and (> egoState PREVENT_NEWROOM)
;**						(or	(TstFlg inQA)
;**								debugging))
;**				(Print "ERROR! You shouldn't be changing rooms in this egoState!")
;**				(= egoState PREVENT_NEWROOM)	
;**			)
		
		(if (or	(and	(not (OneOf n VILLAGE_ROOMS))
							(OneOf curRoomNum VILLAGE_ROOMS))
					(and	(not (OneOf n CASINO_ROOMS))
							(OneOf curRoomNum CASINO_ROOMS))
					(and	(not (OneOf n FAT_CITY_ROOMS))
							(OneOf curRoomNum FAT_CITY_ROOMS))
					(and	(not (OneOf n JUNGLE_ROOMS))
							(OneOf curRoomNum JUNGLE_ROOMS))
					(and	(not (OneOf n SIERRA_ROOMS))
							(OneOf curRoomNum SIERRA_ROOMS)))
			(music fade)
		)

		(ClrFlg cantSave)
		(ClrFlg noCursor)
		(cls)
		
		;**	Keep this filtering to the bottom of hunk:
		(Load FONT bigFont)				;**	HELVETICA_12	0
		(Load FONT userFont)				;**	SERIF_12			1
		(Load FONT smallFont)			;**	SERIF_9			4
		(Load FONT SANS_SERIF_8)		;**						999

		(Load CURSOR normalCursor)
		(Load CURSOR waitCursor)
		(Load CURSOR NO_CURSOR)
		(Load CURSOR KEYBOARD)

		(super newRoom n)
		(= secondsInRoom 0)				;**	(define	HSHUTTER		0)
		(if (< argc 2)						;**	(define	VSHUTTER		1)
			(= showStyle (Random 0 5))	;**	(define	WIPELEFT		2)
		else									;**	(define	WIPERIGHT	3)
			(= showStyle style)			;**	(define	WIPEUP		4)
		)										;**	(define	WIPEDOWN		5)
	)


	(method (startRoom n)
		(LoadMany FALSE
			FILE
			JUMP
			EXTRA
			WINDOW
			TIMER
			FOLLOW
			REVERSE
			DCICON
		
			SHOW_STATE
			DOOR 
			AUTODOOR

;**		These are not used in this game. (Well, maybe Pablo used 'em?)
;**	AVOIDER PATH ORBIT SORTCOPY SIGHT TEXTRA MOUSER RFEATURE GROOPER CAT
;**	DEMO NAMEFIND CHASE SMOOPER SORT COUNT RELDPATH QSCRIPT STOPWALK
;**	TIMEDCUE QSOUND LASTLINK FORCOUNT TRACK GOTOSAID
;**		These self-dispose when done.
;**	LEI CARVING WEAVING LOCKER LOCKER_TEXT 
;**		These are disposed of where used.
;**	GAUGE WANDER DPATH 
		)

		(DisposeScript LOADMANY)

		(if debugOn
			(=	debugOn FALSE)
			(SetDebug)
		)

;**			(if (and (or	debugging 
;**								(TstFlg inQA))
;**						(!= prevRoomNum 260)		
;**						(u> (MemoryInfo FreeHeap) (+ 25 (MemoryInfo LargestPtr))))
;**				(if	(Print "Memory fragmented."
;**							#title	{Oh, oh!}
;**							#button	{Who Cares} 0
;**							#button	{All Right} 1
;**						)
;**					(SetDebug)
;**				)
;**			)

		(soundFX
			stop,
			number		sScored,
		)

		(super startRoom n &rest)

		(if (and (not (OneOf n NO_DEBUG_ROOMS))
					debugging)					(curRoom setLocales DEBUG))
		(if (TstFlg inQA) 					(curRoom setLocales QA))
		(cond
			((OneOf n VILLAGE_ROOMS)		(curRoom setLocales locVillage))
			((OneOf n FAT_CITY_ROOMS)		(curRoom setLocales locFatCity))
			((OneOf n CASINO_ROOMS)			(curRoom setLocales locCasino))
			((OneOf n JUNGLE_ROOMS)			(curRoom setLocales locJungle))
			((OneOf n SIERRA_ROOMS)			(curRoom setLocales locSierra))
		)
;**			(if demo
;**				(SetFlg noCursor)
;**			)
		(if (or	(== egoState WEARING_ZIEGFIELD)
			 		(== normalEgoView vLziegfield))
			(ego
				baseSetter		NormalBase,
			)
		)
	)


	(method (changeScore n)
		(+= score n)
		(if ( > n 0)
			(scoreSound playMaybe)
		)
	)


	(method (doit &tmp [string 50])
		(super doit)

		(if demo
			(User
				canControl		FALSE,
				canInput			FALSE,
			)
		)

		(if (!= oldTime (= oldTime (GetTime TRUE)))
			(if (>= (++ gameSeconds) 60)
				(= gameSeconds 0)
				(if (>= (++ gameMinutes) 60)
					(= gameMinutes 0)
					(++ gameHours)
				)
			)
			(++ secondsInRoom)

			(if (and ( < score 20)
						( > gameMinutes 19)
						( > secondsInRoom 8)	;**	once per room?
						( < secondsInRoom 69)
						(User canControl)
						(== gameSeconds 1))
				(++ gameSeconds)
				(Print "You're getting off to a slow start, Larry.
					Perhaps you should dig out the documentation that came with
					this game and read through the Walk-Thru in your copy
					of \"Nontoonyt Tonite.\"")
				(Print "(This is merely a friendly suggestion from your
					designer and programmer, Al Lowe.)" LOW)
			)

			(if (and	(not (TstFlg preventAutoSave))
						autoSaveMinutes
						(>= (++ asSeconds) 60))
				(= asSeconds 0)
				(if (>= (++ asMinutes) autoSaveMinutes)
					(= asMinutes 0)
					(if (== 1 (Print (Format @string
						"It has been %d minute%s since you saved your game!"
							autoSaveMinutes
							(if (== autoSaveMinutes 1) {} else {s}))
							#title		{AutoSave\05 Warning!}
							#icon			vAutoSave 0 0
							#font			bigFont
							#button		{Save Now!} 1
							#button		{Screw it.} 0
							HIGH
						))
						(theGame save)
					)
				)
			)
		)

		(if (> scoreDisplayed score)
			(if (> machineSpeed pcAT)
				(-- scoreDisplayed)
			else
				(= scoreDisplayed score)
			)
			(StatusLine doit:)
		)

		(if (< scoreDisplayed score)
			(if (> machineSpeed pcAT)
				(++ scoreDisplayed)
			else
				(= scoreDisplayed score)
			)
			(StatusLine doit:)
		)

		(cond										;**	Which cursor now?
			((TstFlg noCursor)
				(self setCursor NO_CURSOR TRUE)
			)
			((== (User controls) FALSE)
				(if (User canInput)
					(self setCursor KEYBOARD (HaveMouse))
				else
					(self setCursor waitCursor (HaveMouse))
				)
			)
			(else
				(self setCursor normalCursor (HaveMouse))
			)
		)

		(if (and curTimer rgTimer)			;**	A global timer that kinda works.
			(-- rgTimer)
		)
	)


	(method (wordFail word input &tmp [string 50])
		(switch (Random 0 4)
			(0		(Print (Format @string "Oh, yeah? 
						Well, \"%s\" this!" word)))
			(1		(Print (Format @string "You may know the word \"%s\" but
						it's beyond Al Lowe's vocabulary!" word)))
			(2		(Print (Format @string "Oh, yeah? 
						Well, I've got your \"%s\" right here!" word)))
			(3		(Print (Format @string "You don't need to type the word
						\"%s\" to complete this game!" word)))
			(else	(Print (Format @string "Don't you ever say the word
						\"%s\" again!" word)))
		)
;**			(LogIt (Format @string "Word \"%s\" in \"%s\"" word input))
	)


	(method (syntaxFail input &tmp [string 40])
		(switch (Random 0 2)
			(0		(Print "That doesn't appear to be a proper sentence."))
			(1		(Print "What in the hell are you talking about?"))
			(else	(Print "That's probably something you could do, 
						but this game won't!"))
		)
;**			(LogIt (Format @string "Syntax in \"%s\"" input))
	)


	(method (pragmaFail input &tmp [string 40])
		(switch (Random 0 2)
			(0		(Print "The hell you say!"))
			(1		(Print "You're too smart for this game!"))
			(else	(Print "Congratulations! You have dumbfounded this game!"))
		)
;**			(LogIt (Format @string "No response to \"%s\"" input))
	)


	(method (handleEvent event &tmp i theLine theObj xyWindow evt fd [string 50])
		(super handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'ascot/place')
				(^= debugging TRUE)
				(if debugging
					(Print "Hi, Al!")
				else
					(Print "\"Goodbye.\"")
				)
			)

;**				((Said 'qa')
;**					(TglFlg inQA)
;**					(TglFlg logging)
;**					(if (not (TstFlg inQA))
;**						(Print "Goodbye, QA-er.")
;**					else
;**						(Print "Welcome to the latest, final version!
;**							Enter today's date in YYMMDD format 
;**							(Halloween is 891031) and your initials 
;**							(up to 3 letters).")
;**						(Format @dateBuffer "8910")
;**						(while ( < (StrLen @dateBuffer) 6)
;**							(GetInput @dateBuffer 8 {Today's date (YYMMDD):})
;**						)
;**						(= initialsBuffer 0)
;**						(while ( < (StrLen @initialsBuffer) 2)
;**							(GetInput @initialsBuffer 4 {Your initials:})
;**						)
;**					)
;**				)

			((or	(Said 'rub/knife')
					(Said 'sharpen/knife'))
				(cond
					((not	(ego has iKnife))
						(DontHave)
					)
					((== (Knife view) ivSharpKnife)
						(ItIs)
					)
					((!= curRoomNum 250)		;**	only sidewalk room
						(Print "There's nothing here suitable for sharpening
							your knife.")
					)
				)
			)

			((or	(Said 'put/anyword/bottle')
					(Said 'fill/bottle'))
				(Print "This is not the correct place to do that.")
			)

			((Said '(pour<out), empty/wine, bottle')
				(cond
					((not (ego has iBottle))
						(DontHave)
					)
					((not egoIsPatti)
						(Print "That's not a good idea, Larry.
							Patti wanted that wine!")
					)
					((== (Bottle view) ivEmptyBottle)
						(Print "The bottle is already empty.")
					)
					(else
						(Bottle
							view		ivEmptyBottle,
						;**		said		'/bottle, empty',
						)
						(Format (Bottle name) "Empty Bottle")
						(Print "Ok. It's empty now.")
					)
				)
			)

			((Said 'cut, carve>')
				(cond
					((not	(ego has iKnife))
						(Print "You have nothing with which to cut anything.")
					)
					((== (Knife view) iKnife)
						(Print "Your knife is too dull to cut anything.")
					)
					((Said '[/noword]')
						(Print "What do you want to cut?")
					)
					((Said '/grass')
						(Print "This grass isn't really suitable. Keep looking.")
					)
					((not (Said '/sculpture, wood'))
						(Print "No; find something else to carve.")
					)
					((not	(ego has iWood))
						(Print "You have nothing to carve.")
					)
					((or	(== (Wood view) ivSculpture)
							(== (Wood view) ivSculptureModest))
						(Print "You've already done the best you can.")
					)
					((or	(== egoState NORMAL)
							(== egoState WEARING_GRASS_SKIRT))
						(Ok)
						(theGame setScript (ScriptID CARVING))
					)
					(else
						(NotNow)
					)
				)
				(event claimed TRUE)
			)


			((or	(Said 'use/orchid/lei<make')
					(Said 'weave, make/orchid, lei'))
				(cond
					((not	(ego has iOrchids))
						(Print "You have no flowers with which to make a lei.")
					)
					((== (Orchids view) ivLei)
						(Print "You've already made a lei from the orchids.")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(Ok)
						(theGame setScript (ScriptID LEI))
					)
				)
			)

			((Said 'wear, (change<into), (put<on)/orchid, lei')
				(cond
					((not	(ego has iOrchids))
						(Print "An interesting thought_-- but how?")
					)
					((!= (Orchids view) ivLei)
						(Print "Right now, they're just a beautiful bunch of
							orchids.")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(Print "You're close, but you are not the person for whom
							they are intended.")
					)
				)
			)

			((or	(Said 'use/grass/skirt<make')
					(Said 'weave, make/grass, skirt'))
				(cond
					((not	(ego has iGrass))
						(Print "You have no grass.")
					)
					((== (Grass view) ivGrassSkirt)
						(Print "You've already made a skirt from the grass.")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(Ok)
						(theGame setScript (ScriptID WEAVING))
					)
				)
			)

			((or	(Said 'get/naked')
					(Said '(take<off), remove/suit, clothes'))
				(Print "Please! Not here.")
			)

			((Said 'change/suit, clothes')
				(Print "Be more specific. Say what you want to wear.")
			)

			((Said 'wear, (change<into), (put<on)/grass, skirt')
				(cond
					((not	(ego has iGrass))
						(Print "An interesting thought -- but how?")
					)
					((not (== (Grass view) ivGrassSkirt))
						(Print "It would be difficult to wear a clump of grass.")
					)
					(else
						(Print "A good idea,
							but you're far too modest to change clothes here.")
					)
				)
			)

;**				((Said 'stop, (dont<show), quit, (turn<off), cancel/credit, plot')
;**					(if (!= egoState NORMAL)
;**						(NotNow)
;**					else
;**						(Print "Ok. You'll see no more credits now.")
;**						(for ((= i beenIn200)) (<= i sawCredits260) ((++ i))
;**							(SetFlg i)
;**						)
;**						(if (== egoState TROPIC)
;**							(= normalEgoView vLarry)
;**							(NormalEgo)
;**						)
;**					)
;**				)

			((and (ego has iSoap)
					(Said 'use/soap'))
				(Print "A nice shower WOULD feel good!")
			)

			((or	(Said 'unknownNumber/')
		 			(Said '/unknownNumber')
		 			(Said '//unknownNumber'))
				(Print "That's may do something somewhere but not here!")
			)

			((and (ego has iDivorce)
					(not (ego has iKeycard))
					(Said 'look, read/divorce, paper, document'))
				(ego get iKeycard)
				(theGame changeScore 100)
				(Print "Well, well. Look at this.
					Somehow Suzi Cheatem's Fat City Membership Card got mixed up
					in these papers. What a sleuth you are!")
			)

			((and (ego has iKeycard)
					(or	(Said 'read<back/keycard, card')
							(Said 'read/back/keycard, card')
							(Said 'turn/keycard, card')
							(Said 'read/combination/keycard, card')))
				(Ok)
				(theGame setScript (ScriptID LOCKER))
			)

;**				((and (ego has iCreditCard)
;**						(Said '/card'))
;**					(Print "Everyone living on this island knows you and your
;**						credit card are no good here.")
;**				)

			((Said 'count>')
				(=	theObj (inventory saidMe))
				(cond
					((Said '[/noword]')
						(Print "What do you want to count?")
					)
					((not	theObj)
						(event claimed TRUE)
						(Printf "You quickly count to %d. Such fun, eh?" 
							(Random 10 999))
					)
					((!= (inventory indexOf theObj) iMoney)
						(Print "How can you count that?")
					)
					((not	(theObj ownedBy ego))
						(Print "You're busted!")
					)
					(else
						(Printf "You have %d dollars." dollars)
					)	
				)
			)

			((or	(Said 'give/bill, money')
					(Said 'bribe'))
				(cond
					((not (ego has iMoney))
						(Print "You have no money.")
					)
					((== (Money view) ivOnes)
						(Print "\"How about a few of these ones?\" you ask."
							#icon (Money view) 0 0
						)
						(Print "(Evidently no one is interested.)" LOW)
					)
					((== (Money view) ivTips)
						(Print "\"Hey! Can I give you a tip?\" you say."
							#icon (Money view) 0 0
						)
						(Print "\"Guess not,\" you think.")
					)
					(else							;**	Has $20
						(Print "\"I've got twenty bucks on me,\" you announce.
							\"Anybody interested?\""
							#icon (Money view) 0 0
						)
						(Print "There is no response.")
					)
				)
			)

			((and	(ego has iGrass)
					(== (Grass view) ivGrassSkirt)
					(or	(Said 'get/dressed')
							(Said 'get<dressed')
							(Said 'wear, change, (take<off), remove, (put<on)/
																	skirt, clothes, suit')))
				(Print "A good idea, but you can't change here!")
			)

			((and	(ego has iBra)
					(Said 'put//(bra)>'))
				(=	theObj (inventory saidMe))
				(cond
					((Said '[/noword]')
						(Print "What did you want to put inside your bra?")
					)
					((not	theObj)
						(event claimed TRUE)
						(Print "You can't do that here; at least, not now.")
					)
					((not	(theObj ownedBy ego))
						(DontHave)
					)
					(else
						(Print "Ok. You put it in. It fits. You remove it."
							#icon iBra 0 0
						)
						(Print "(Pretty much fun, eh?)" LOW)
					)
				)
			)

			((and (ego has iKnife)
					(or	(Said 'use/knife')
							(Said 'stab')))
				(Print "Hey! We want our R rating for sex, not violence!")
			)

			((Said 'open, (look<in)>')
				(=	theObj (inventory saidMe))
				(cond
					((Said '[/noword]')
						(Print "What do you want to inspect?")
					)
					((not	theObj)
						(event claimed TRUE)
						(Print "You can't do that here; at least, not now.")
					)
					((not	(theObj ownedBy ego))
						(Print "You see nothing special.")
					)
					(else
						(switch (inventory indexOf theObj)
							(iOrchids
								(Print "The orchids look perfectly safe."
									#icon iOrchids 0 0
								)
							)
							(iBottle
								(switch (Bottle view) 
									(ivEmptyBottle
										(Print "The bottle is empty. Not even a note!"
											#icon ivEmptyBottle 0 0
										)
									)
									(ivWaterBottle
										(Print "The water is still inside the bottle."
											#icon ivWaterBottle 0 0
										)
									)
									(else
										(Print "The bottle is full of wine."
											#icon iBottle 0 0
										)
									)
								)
							)
							(iPanties
								(Print "\"100% Pure Silk\""
									#icon iPanties 0 0
								)
							)
							(iPantyhose
								(Print "\"Extra Sheer\""
									#icon iPantyhose 0 0
								)
							)
							(iBra
								(Print "\"36C\""
									#icon iBra 0 0
								)
							)
							(iDress
								(Print "You look pretty good,
									if you do think so yourself!"
										#icon iDress 0 0
									)
							)
							(iCoconuts
								(Print "You can not open the coconuts."
									#icon iCoconuts 0 0
								)
							)
							(else
								(Print "You see nothing special.")
								(LameResponse)
							)
						)
					)
				)
			)


			;**	Do the silly stuff now

			((Said 'hello')
				(Print "\"Hi.\"")
			)

			((or	(Said '/bye')
					(Said 'bye'))
				(Print "\"Goodbye.\"")
			)

			((Said 'thank')
				(Print "\"You're welcome.\"")
			)

			((Said 'knock')
				(Print "\"Knock, knock.\"")
				(Print (Format @string "(Nobody wants to play that game, %s!)"
					egoName) LOW)
			)

			((Said 'hit')
				(Print "Hey! We want our R rating for sex, not violence!")
			)

			((or	(Said 'go/toilet')
					(Said 'urinate')
					(Said 'take/urinate'))
				(cond
					(egoIsPatti
						(Print "Patti!")
						(Print "(Really!)" LOW)
					)
					(else
						(Print "A warm feeling spreads down your leg.")
					)
				)
			)

			((Said 'climb>')
				(cond
					((Said '/wall, building')
						(Print "Climbing the walls already?")
					)
					(egoIsPatti
						(Print "You'd better not. You might rip your dress.")
					)
					(else
						(Print "That would be unbecoming a man of leisure.")
					)
				)
				(event claimed TRUE)
				;**	(LameResponse)
			)

			((or	(Said '//larry')
					(Said '/larry'))
				(cond
					((not egoIsPatti)
						(Print "Talking to yourself again, Larry?")
					)
					((< curRoomNum 590)
						(Print "You really miss him, don't you, Patti?")
					)
					(else
						(Print "You are so glad you two are back together!")
					)
				)
			)

			((Said 'jump, dance')
				(Print "Whee!")
			)

			((Said 'sing')
				(Print "\"FEELINGS! NOTHING MORE THAN FEELINGS!!\"")
			)

			((Said 'wait')
				(Print "Isn't that what you are doing?")
			)

			((Said 'pick')
				(Print "If you pick it, it won't heal!")
			)

			((Said 'yell')
				(Print "\"HELP!\"")
			)

			((Said 'steal')
				(Print "That's against the law!")
			)

			((or	(Said 'no')
					(Said 'yes'))
				(Ok)
			)

			((Said 'borrow')
				(Print "Who would loan you anything?")
			)

			((Said 'cheat')
				(Print "Ok, you win.\05")
				(Print "(Game over.)" LOW)
				(= quit TRUE)
			)

			((Said '(put<on), wear>')
				(cond
					((Said '[/noword]')
						(Print "What do you want to wear?")
					)
					((= theObj (inventory saidMe))
						(cond
							((not	(ego has (inventory indexOf theObj)))
								(DontHave)
							)
							((or	(== theObj iBra)
									(== theObj iDress)
									(== theObj iPanties)
									(== theObj iPantyhose))
								(YouAre)
							)
							(else
								(Print "You'd look better if you didn't!")
							)
						)
					)
					(else
						(Print "You can't wear what is not yours.")
						(event claimed TRUE)
						;**	(LameResponse)
					)
				)
			)

			((Said 'drop>')
				(cond
					((Said '[/noword]')
						(Print "What do you want to drop?")
					)
					((= theObj (inventory saidMe))
						(if (not	(ego has (inventory indexOf theObj)))
							(DontHave)
						else
							(Print "Nah. Better not!")
						)
					)
					(else
						(Print "It's not yours to drop.")
						(event claimed TRUE)
						;**	(LameResponse)
					)
				)
			)

			((Said 'throw>')
				(cond
					((Said '[/noword]')
						(Print "What do you want to throw?")
					)
					((= theObj (inventory saidMe))
						(if (not	(ego has (inventory indexOf theObj)))
							(DontHave)
						else
							(Print "Nah. You might need it later!")
						)
					)
					(else
						(Print "That doesn't belong to you.")
						(event claimed TRUE)
						;**	(LameResponse)
					)
				)
			)

			((Said 'smell')
				(Print "Sniff, sniff.")
				(cond
					((TstFlg needsShower)
						(Print "You should have showered after that workout!")
					)
					((TstFlg needsSoap)
						(Print "You should have used soap during your shower!")
					)
					((TstFlg needsDeodorant)
						(Print "Your deodorant has failed!")
					)
				)
			)

			((Said 'whistle')
				(Print "You whistle a happy tune.")
			)

			((Said 'ask/job')
				(Print "\"Is there any chance I could get a job around here?\" 
					you say.")
				(Print "(There is no response.)" LOW)
			)

			((and egoIsPatti
					(or	(Said '/arnold')
							(Said '//arnold')))
				(Print "Forget about that creep, Patti!")
			)

			((Said 'laugh')
				(Print "\"Ha, ha!\"")
			)

			((Said 'eat')
				(Print "Once you tasted it, you wouldn't want it!")
			)

			((Said 'rest, sleep')
				(Print "There's no time!")
			)

			((Said 'help/me, self')
				(Print "Stop your whining!")
			)

			((Said 'help')
				(Print "How would you help?")
			)

			((Said 'search>')
				(if (Said '/pocket, pants, patti, larry')
					(Print "Searching through your clothes you find...")
					(inventory showSelf	ego)
				else
					(event claimed TRUE)
					(Print "You find nothing.")
				)
			)


			;**	Now handle the potty-mouths:

			((or	(Said '/hump/you')
					(Said 'hump/you'))
				(Print "\"Same to you, buddy!\"")
			)

			((Said 'hump/anyword')
				(Print "Yeah, you probably would, too!")
			)

			((or	(Said 'fondle/me, larry, self')
					(Said 'masturbate'))
				(Print "Aren't your palms hairy enough?")
			)

			((Said 'touch, stroke, kiss, look/pussy')
				(Printf "Obviously, restraint is no problem for you, %s." egoName)
			)

			((Said 'lick/girl')
				(Print "That comes later.")
			)

			((or	(Said 'look<in/blouse')
					(Said 'look<up/skirt'))
				(Print "You see something special.")
			)

			((Said 'drink')
				(Print "Nobody's THAT thirsty!")
			)

			((or	(Said 'use/key')
					(Said 'unlock'))
				(Print "It's not that easy!")
			)

			((or	(Said '/doco')
					(Said '//doco'))
				(Print "It's in the box your disks came in.
					Have you read it?")
			)

			((Said 'listen')
				(Print "If you are very quiet, you can hear your keyboard click.")
			)

			((Said 'kiss')
				(Print "Wouldn't you rather just remain friends?")
			)

			((or	(Said 'suck, hump/me')
					(Said 'crap, piss, hump, fart')
					(Said '/crack, crap, piss, hump, fart, cunnilingus')
					(Said '//crack, crap, piss, hump, fart, cunnilingus'))
				(Print "Tsk, tsk.")
			)

			((or	(Said '/hell')
					(Said '//hell'))
				(Print "That's probably where you'll end up!")
			)


			;**	Now handle the TRULY stupid answers:

			;**	Start with 'look'

			((Said 'look>')
				(cond
					((Said '/self, larry, patti, me, shirt, clothes, pants')
						(if (not egoIsPatti)
							(Print "You look pretty good, if you do say so yourself!")
						else
							(Print "You look great, no matter what you wear!")
						)
					)
					
					((and	(== (ego view) vLtropic)
							(Said '/sunglass'))
						(Print "You're too cool!")
					)

					((Said '/foot, shoe')
						(if (== (ego view) vLtropic)
							(Print "Your toes are so ugly!")
						else
							(Print "Your feet should hurt, what with all this 
								walking!")
						)
					)

					((Said '/bush, tree')
						(Print "You see many leaves.")
					)

					((Said '/man, woman, people')
						(Print "Where?")
					)

					((Said '/wall, building')
						(Print "Staring at the walls already?")
					)

					((Said '/ground, down')
						(Print "It just lies there, under your feet.")
					)

					((Said '/sky, ceiling')
						(Print "It's still up there!")
					)

					((Said '/tit')
						(if egoIsPatti
							(Print "Hey! That's Larry's line!")
						else
							(Print "You really love this, don't you!")
						)
					)

					((Said '/ass')
						(if egoIsPatti
							(Print "Tight butts drive you nuts!")
						else
							(Print "Has it been 30 seconds already?")
						)
					)

					((Said '<in/window')
						(Print "This game does not encourage voyeurism!")
									;**	\"voyeurism\" spellchecked September 3, 1988
					)

					((= theObj (inventory saidMe))
						(if (theObj ownedBy ego)
							(theObj showSelf)
						else
							(Print "You see nothing special.")
						)
					)

					(else
						(switch (Random 42 44)
							(42	(Print "It's just as it appears."))
							(43	(Print "It doesn't look interesting."))
							(44	(Print "You see nothing special."))
						)
						(event claimed TRUE)
						(LameResponse)
					)
				)
			)


			((or	(Said 'use, spend/bill, tips, money')
					(Said 'buy'))
				(if (ego has iMoney)
					(Print "There's nothing worth buying here.")
				else
					(Print "You're broke!")
				)
			)

			((Said 'use>')
				(=	theObj (inventory saidMe))
				(event claimed FALSE)
				(cond
					((Said '[/noword]')
						(Print "What do you want to use?")
					)
					((not	theObj)
						(Print "You can't do that here; at least, not now.")
					)
					((not	(theObj ownedBy ego))
						(DontHave)
					)
					(else
						(Print "There's no need to use it here.")
						(LameResponse)
					)
				)
				(event claimed TRUE)
			)

			((Said 'give>')
				(= theObj (inventory saidMe))
				(event claimed FALSE)
				(cond
					((Said '/anyword[/noword]')
						(Print "To whom?")
					)
					((Said '[/noword]')
						(Print "What do you want to give?")
					)
					((not theObj)
						(Print "It's not yours to give.")
					)
					((not	(theObj ownedBy ego))
						(DontHave)
					)
					(else
						(Print "You have no reason to do that.")
						(LameResponse)
					)
				)
				(event claimed TRUE)
			)

			((Said 'get<down')
				(Print "You get down from a duck, but not here!")
			)

			((Said 'get<up')
				(Print "You already are \"up for the game!\"")
			)

			((Said 'get>')
				(cond
					((Said '[/noword]')
						(Print "What do you want to take?")
					)
					((and (= theObj (inventory saidMe))
							(theObj ownedBy ego))
						(Print "You already have it.")
					)
					((== theObj iWood)
						(Print "There is no wood here that strikes your fancy.")
					)
					(else
						(switch (Random 33 35)	;**	Larry 1 memorial print numbers
							(33 (Print "It's of no use."))
							(34 (Print "You don't need it."))
							(35 (Print "There's no reason to take it."))
						)
						(LameResponse)
					)
				)
				(event claimed TRUE)
			)

			((= theObj (inventory saidMe))
				(if (not (theObj ownedBy ego))
					(DontHave)
				else
					(Print "You can't do that now.")
				)
			)

			((Said 'talk>')
				(if (Said '[/noword]')
					(Print "To whom?")
				else
					(Print "\"Hello,\" you say.")
					(Print "(There is no response.)" LOW)
					(event claimed TRUE)
				)
				;**	(LameResponse)
			)

			((or	(Said '/pussy, crap, pee, hump, asshole, tit, ass, cunnilingus')
					(Said '//pussy, crap, pee, hump, asshole, tit, ass, cunnilingus'))
				(Print "Ok, but I don't usually do that!")
			)

			((or	(Said '//woman')
					(Said '/woman/')
					(Said '/woman'))
				(Print "She doesn't seem interested in that!")
			)
		)
	)
)


(class Iitem of InvItem
	(method (showSelf)
;		(invIcon view view)
		(Print INVENTORY_TEXT view
			#title		name
;			#icon 		invIcon
			#icon			view 0 0
		)
	)
)
;(instance invIcon of DCIcon)

;**		Handle the inventory items.  Their view numbers were assigned to
;**	match their inventory numbers, so only the "i" type define is needed.
;**	Items with more than one view also have "iv" assigns.  Names and Saids
;**	must be as long as their longest alteration.

(instance Dummy of Iitem
	(properties
		name		{Nothing}
	)
)
(instance CreditCard of Iitem
	(properties
		view		iCreditCard
;		owner		0
		name		{Credit Card}
	)
	(method (saidMe event)
		(cond
			((ego has iKeycard)
				(return FALSE)
			)
			(else
				(return (Said '/card[<credit]'))
			)
		)
	)
)
(instance Knife of Iitem
	(properties
		view		iKnife
;		owner		0
		name		{Ginsu Knife}
;later becomes "Sharp Knife"
		said		'/knife'
	)
)
(instance Wood of Iitem
	(properties
		view		iWood
;		owner		0
		name		{Granadilla Wood_}
;later becomes "Erotic Sculpture"
	)
	(method (saidMe event)
		(cond
			((== view ivSculpture) 
				(return (Said '/wood, sculpture'))
			)
			(else
				(return (Said '/wood'))
			)
		)
	)
)
(instance Grass of Iitem
	(properties
		view		iGrass
		owner		230
		name		{Native Grass}
;later becomes "Grass Skirt"
	)
	(method (saidMe event)
		(cond
			((== view ivGrassSkirt) 
				(return (Said '/skirt'))
			)
			(else
				(return (Said '/grass'))
			)
		)
	)
)
(instance Soap of Iitem
	(properties
		view		iSoap
		owner		253
		name		{Soap-On-A-Rope}
		said		'/soap'
	)
)
(instance Money of Iitem
	(properties
		view		iMoney
;		owner		0
		name		{A Twenty Dollar Bill}
;later becomes "500 Dollar Bills"
;later becomes "$43.00 in Tips"
	)
	(method (saidMe event)
		(cond
			((== view ivOnes) 
				(return (Said '/money, ones, 500'))
			)
			((== view ivTips) 
				(return (Said '/money, tips, jar, 43'))
			)
			(else
				(return (Said '/money, 20, bill, twenty'))
			)
		)
	)
)
(instance Deed of Iitem
	(properties
		view		iDeed
;		owner		320
		name		{Land Deed}
		said		'/deed'
	)
)
(instance Towel of Iitem
	(properties
		view		iTowel
;		owner		0
		name		{Beach Towel}
		said		'/towel'
	)
)
(instance Keycard of Iitem
	(properties
		view		iKeycard
;		owner		0
		name		{Spa Keycard}
		said		'/keycard, card'
	)
)
(instance Divorce of Iitem
	(properties
		view		iDivorce
;		owner		0
		name		{Divorce Decree}
		said		'/divorce, decree'
	)
)
(instance Orchids of Iitem
	(properties
		view		iOrchids
		owner		235
		name		{some Orchids}
;later becomes "A Quick Lei"
	)
	(method (saidMe event)
		(cond
			((== view ivLei) 
				(return (Said '/orchid, lei'))
			)
			(else
				(return (Said '/orchid'))
			)
		)
	)
)
(instance Key of Iitem
	(properties
		view		iKey
		owner		450
		name		{Penthouse Key}
;later becomes "Your Key"
		said		'/key'
	)
)
(instance Bottle of Iitem
	(properties
		view		iBottle
;		owner		0
		name		{Bottle of Wine_}
;later becomes "Empty Bottle"
;later becomes "Bottle of Water"
	)
	(method (saidMe event)
		(cond
			((== view ivEmptyBottle) 
				(return (Said '/bottle'))
			)
			((== view ivWaterBottle) 
				(return (Said '/bottle, water'))
			)
			(else
				(return (Said '/bottle, wine'))
			)
		)
	)
)
(instance Panties of Iitem
	(properties
		view		iPanties
		owner		484
		name		{Panties}
		said		'/panties'
	)
)
(instance Pantyhose of Iitem
	(properties
		view		iPantyhose
		owner		484
		name		{Pantyhose}
		said		'/pantyhose'
	)
)
(instance Bra of Iitem
	(properties
		view		iBra
		owner		484
		name		{Bra}
		said		'/bra'
	)
)
(instance Dress of Iitem
	(properties
		view		iDress
		owner		484
		name		{Dress}
		said		'/dress'
	)
)
(instance Marker of Iitem
	(properties
		view		iMarker
;		owner		0
		name		{Magic Marker}
		said		'/marker'
	)
)
(instance Coconuts of Iitem
	(properties
		view		iCoconuts
		owner		530
		name		{Coconuts}
		said		'/coconut'
	)
)
(instance Marijuana of Iitem
	(properties
		view		iMarijuana
		owner		530
		name		{Marijuana}
;later becomes "Hemp Rope"
		said		'/marijuana, marijuana'
	)
	(method (saidMe event)
		(cond
			((== view ivRope) 
				(return (Said '/pot, rope'))
			)
			(else
				(return (Said '/pot'))
			)
		)
	)
)


(instance statusCode of Code
	(method (doit str)
		(Format str " Score: %d of 4000 %7s %s" scoreDisplayed " " 
			(if egoIsPatti {Passionate Patti}
			else				{Leisure Suit Larry 3}))
	)
)

(instance egoObj of Ego
	(properties
		name 			"ego"
		view			vLarry
		y				1111
	)
)

(instance scoreSound of Sound
	(properties
		number		sScored
		priority		10
		keep			TRUE
	)
)
(instance theMusic of Sound
	(properties
		number 		sScored
		priority		0
		keep			TRUE
	)
)
(instance theSoundFX of Sound
	(properties
		number 		sScored
		priority		5
		keep			TRUE
	)
)

(instance theWindow of SysWindow
	(method (open)
		(if ( < (Graph GDetect) 9)			;**	Monochrome or CGA
			(if (or	( < color 7) 
						(== color 8))
				(= color black)				;**	black on white
				(= back  ltWhite) 
			else
				(= color ltWhite)				;**	default
				(= back  black)
			)
		)
		(super open)
	)
)

(instance NormalBase of Code
	(method (doit &tmp w)
		(if (== curRoomNum 253)	(= w 22)	;**		'cause L gets in trouble 
		else						  	(= w 10));**	around the porta-potty.
		(ego brBottom		(+ (ego y) 1))
		(ego brTop			(- (ego brBottom) (ego yStep)))
		(ego brLeft			(- (ego x) w))
		(ego brRight		(+ (ego x) w))
	)
)

;**	Take any known instance of ego-jockeying, and return him to his normal,
;**	wonderful self.

(procedure (NormalEgo theLoop theView)
	(HandsOn)
	(ego edgeHit FALSE)
	(switch argc
		(0	(NormalActor ego (ego loop) (if ( > argc 1) theView else normalEgoView)))
		(1	(NormalActor ego theLoop (if ( > argc 1) theView else normalEgoView)))
		(2	(NormalActor ego theLoop theView))
	)
)

(procedure (NormalActor who theLoop theView)
	(if ( > argc 1) (who loop theLoop))
	(if ( > argc 2) (who view theView))
	(who
		setLoop			RELEASE,
		setPri			RELEASE,
		setStep			3 2,
		setCycle			Walk,
		illegalBits		cLtWhite,
		cycleSpeed		0,
		moveSpeed		0,
		ignoreActors	FALSE,
	)
)

(procedure (HandsOff)
	(User
		canControl		FALSE,
		canInput			FALSE
	)
	(ego setMotion	FALSE)
)

(procedure (HandsOn)
	(User
		canControl		TRUE,
		canInput			TRUE
	)
	(ego setMotion	FALSE)
)

(procedure (cls)
	(if modelessDialog
		(modelessDialog dispose)
	)
)

(procedure	(Ok)				(Print "O.K."))
(procedure	(ItIs)			(Print "It is."))
(procedure	(YouAre)			(Print "You are."))
(procedure	(NotNow)			(Print "Good idea. You might try that again later."))
(procedure	(NotClose)		(Print "You're not close enough."))
(procedure	(AlreadyTook)	(Print "You already took it."))
(procedure	(DontHave)		(Print "You don't have it."))

(procedure (Notify whom)
	(= whom (ScriptID whom))
	(whom notify &rest)
)

(procedure (HaveMem howMuch)
	(if (> (MemoryInfo FreeHeap) howMuch)
		(return TRUE)
	else
		(Print "You can't do that here; at least, not now.")
		(return FALSE)
	)
)


;**	This will add an actor to the picture at his current location and
;**	appearance, then position him below the screen so he can be used
;**	again as someone else.

(procedure (AddActorToPic whom)
	(if whom
		((View new)
			view			(whom view),
			loop			(whom loop),
			cel			(whom cel),
			setPri		(whom priority),
			posn			(whom x) (whom y),
			addToPic
		)
		(whom
			posn			(whom x) ( + 1000 (whom y))
		)
	)
)

(procedure	(SetRgTimer name minutes seconds)
	(= curTimer name)
	(= rgTimer (* 10 (+ seconds (* minutes 60))))
)

(procedure (LogIt why &tmp [string 10] [string1 60])
(return)											;**	just in case it gets called
;**		(if (TstFlg logging)
;**			(Format @string "Log%s.%s" (theGame name) @initialsBuffer)
;**			(Format @string1 ";[%3d %s %s %3s %3d %3d/%3d] %s\r"
;**								   curRoomNum version @dateBuffer 
;**									@initialsBuffer  (ego view) (ego x) (ego y) why)
;**			(File name @string, write @string1, close)
;**		)
)

(procedure (LameResponse &tmp [string 50])
(return)											;**	just in case it gets called
;**		(if (TstFlg inQA)
;**			(LogIt (Format @string "Lame response to \"%s\"" (User inputLineAddr)))
;**		)
)

(procedure (SetFlg flag)
	(|= [flagArray (/ flag 16)] (>> $8000 (mod flag 16)))
)

(procedure (ClrFlg flag)
	(&= [flagArray (/ flag 16)] (~ (>> $8000 (mod flag 16))))
)

(procedure (TglFlg flag)
	(^= [flagArray (/ flag 16)] (>> $8000 (mod flag 16)))
)

(procedure (TstFlg flag)
	(return (if (& [flagArray (/ flag 16)] (>> $8000 (mod flag 16))) TRUE else FALSE))
)

(procedure (InRoom iEnum room)			;**	returns TRUE if ObjInRoom
	(return (== ((inventory at iEnum) owner) (if ( < argc 2) curRoomNum else room)))
)

(procedure (PutInRoom iEnum room)		;**	Moves iEnum to someplace
	((inventory at iEnum) owner	(if ( < argc 2) curRoomNum else room))
)

(procedure (PrintDelay string)
	(return (+ 3 (/ (StrLen string) stringDelay)))
)

;**	(procedure (LoadMany what which &tmp i theRes)
;**		(for ((-= argc 2)(= i 0)) (<= i argc) ((++ i))		
;**			(= theRes [which i])
;**			(if what (Load what theRes)
;**			else		(DisposeScript theRes)
;**			)
;**		)
;**	)
