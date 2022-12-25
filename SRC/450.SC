;**
;**	Logics for room 450 -- Passionate Patti's Piano Bar
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 17, 1990
;**


(script#				450)

(public
	rm450				0
)


;**		When L gets his clothes from backstage, and sets showroomState to
;**	SRdone, P shows up for work.


(define	vRoom							450)
	(define	lMisc					0)
		(define	cPiano		0)
		(define	cStool		1)
		(define	cTips			2)
		(define	cBoard1		3)
		(define	cBoard2		4)
		(define	cMarker		5)
	(define	lMarker				1)

(define	vLroom						451)
	(define	lLsitting			0)
	(define	lLlookingLeft		1)
	(define	lLlookingRight		2)

(define	vProom						452)
;**	loops 0-3 are random piano playing
	(define	lPtalking			4)

(define	vDrinkers					453)
	(define	lRoger				0)
	(define	lRogerLookingLeft	1)
	(define	lElvis				2)
	(define	lElvisLookingLeft	3)
	(define	lElvisWinking		4)


(define	sPattiPiano1				451)	
(define	sPattiPiano2				452)	
(define	sPattiPiano3				453)	
(define	sPattiPiano4				454)	

;(define	sFanfare						110)	
;(define	sTitle						120)
;(define	sVoyeur						206)	
;(define	sKeystone					261)
;(define	sTawni						265)	
;(define	sSuzi							323)	
;(define	sStripMusic					330)
;(define	sDale							335)
;(define	sBambi						395)
;(define	sFatCityRegion				399)
;(define	sCherriDancing				431)	
;(define	sCherri						435)	
;(define	sPbamboo						500)
;(define	sFeralPig					540)
;(define	sWhitewaterRafting		560)
;(define	sJungleRegion				599)


(define	aboveStool 				(& (ego onControl) cBlue))
(define	nearWrongStools 		(& (ego onControl) cGreen))
(define	nearRoger				(& (ego onControl) cCyan))
(define	belowStool 				(& (ego onControl) cRed))
(define	besideStool 			(& (ego onControl) cMagenta))
(define	nearBooths				(& (ego onControl) cBrown))
(define	nearMarker				(ego inRect  10 152  55 160))
(define	nearTips					(ego inRect 138 105 171 118))

(define	sittingPosn				139 121)


(enum												;**	PattiScript state values
	PattiInit
	playPiano
	oneMoreTime
	stopPlayingPiano
	howAbout
	neverPlayAgain
	leave
	atLarry
	soLongLarry
	centeredOnDoor
	outtaHere
)


(enum												;**	RoomScript state values
	intro
	demo1
	demo2
	demo2a
	demo3
	demo4
	sitYourAssDown
	walkNorth
	walkEast
	okSit
	sitting
	standUp
	doneStanding
	goingTo455
	changeRooms
)


(enum												;**	ElvisScript state values
	ElvisInit
	doSomethingElvis
	returnToElvis
	winkForRogerElvis
)


(enum												;**	RogerScript state values
	RogerInit
	doSomethingRoger
	returnToNormalRoger
)


(synonyms
	(menu sign board special )
	(counter booth )
	(patti girl player )
	(tip tips jar glass money snifter )
	(board blackboard special sign menu )
)

(local
	elvisWinked
	pattiAtPiano
	request
	seenMsg
)


(instance rm450 of Room
	(properties
		picture		450
		south			416
		west			416
	)


	(method (init)
;**			(if demo
;**				(= egoIsPatti TRUE)
;**				(= normalEgoView vPatti)
;**				(= egoName (Format @egoNameBuffer "Patti"))
;**			)

		(Load VIEW vLroom)

		(super init)

		(addToPics
			add 				atpPiano,
			add 				atpChair1,
			add 				atpChair2,
			add 				atpChair3,
			add 				atpChair4,
			add 				atpChair5,
			add				(if (and egoIsPatti
											(InRoom iMarker))	atpBlackboard2
								else 								atpBlackboard1),
			doit
		)

		(self
			setScript	 		RoomScript
		)

		(if (and egoIsPatti
					(InRoom iMarker))
			(aMarker			init)
		)

		(NormalEgo)
		(cond
;**				(demo
;**					(ego
;**						posn				123 128,
;**						loop				facingWest,
;**					)
;**				)
			((== egoState DOING_IT)
				(aPatti
					view				vPatti,
					init,
					loop				facingWest,
					posn				168 107,
					setCycle			Walk,
				)
				(ego
					ignoreActors,
					illegalBits		0,
					posn				sittingPosn,
					view				vLroom,
				)
				(RoomScript changeState sitting)
				(PattiScript changeState leave)
				(HandsOff)
			)
			((== prevRoomNum 455)
				(= pattiAtPiano TRUE)
				(HandsOff)
				(ego
					ignoreActors,
					illegalBits		0,
					posn				sittingPosn,
					view				vLroom,
				)
				(RoomScript changeState sitting)
			)
			(else
				(if (and (== showroomState SRdone)	;**	L has his clothes again
							(InRoom iKey))					;**	but not Patti's room key
					(= pattiAtPiano TRUE)
	
					(if (== (Random 0 3) 3)
						(aRoger		init)
					)
	
					(if (== (Random 0 3) 3)
						(aElvis		init)
					)
				)
				(if ( > (ego y) 130)
					(ego posn  29 188)
				else
					(ego posn   9 168)
				)
			)
		)
		(ego 	init)

		(if pattiAtPiano
			(aPatti				init)
			(PattiScript changeState playPiano)
			(blockPatti			init)
			(SetFlg tipsIn450)				;**	Patti's tips are also in lounge.
			(ego
				observeBlocks		blockPatti,
				observeControls	cYellow,
			)
		)

		(if (TstFlg tipsIn450)			
			(aTips			init)
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (& (ego onControl) cLtBlue)
			(if (not seenMsg)
				(= seenMsg TRUE)
				(ego
					posn 				(ego xLast) (ego yLast),
					setMotion		FALSE,
					observeControl cLtBlue,
					forceUpd,
				)
				(if egoIsPatti
					(Print "You have no need to use the employee's exit, Patti.")
				else
					(Print "This exit is marked \"For Employees Only.\"
						You are no longer an employee of Natives, Inc.")
				)
			)
		else
			(= seenMsg FALSE)
		)

		(if pattiAtPiano
			(ego
				observeBlocks		blockPatti,
				observeControls	cYellow,
			)
		)
	)


	(method (handleEvent event)
;**			(if (and (not (super handleEvent event))
;**						(not (event claimed))
;**						modelessDialog
;**						demo
;**						(== (event message) ENTER)
;**						(== (event type) keyDown))
;**				(event claimed TRUE)
;**				(cls)
;**				(self cue)
;**			)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'sit')
				(cond
					((== egoState SITTING)
						(YouAre)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(egoIsPatti
						(Print "You have no desire to spend any more time here!")
					)
					(nearWrongStools
						(Print "These two stools are reserved for high rollers only.")
					)
					((or	aboveStool
							besideStool
							belowStool)
						(self changeState sitYourAssDown)
					)
					(nearBooths
						(Print "The booths are reserved for high rollers only.")
					)
					(else
						(Print "There is a stool available right beside the
							keyboard.")
					)
				)
			)

			((or	(Said 'rise, (get<off), (get<up), (stand<up)')
					(Said 'leave/stool, chair'))
				(cond
					((== egoState NORMAL)
						(YouAre)
					)
					((!= egoState SITTING)
						(NotNow)
					)
					(else
						(self changeState standUp)
					)
				)
			)

			((Said 'get/marker')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not egoIsPatti)
						(Print "Where?")
					)
					((not (InRoom iMarker))
						(AlreadyTook)
					)
					((not nearMarker)
						(NotClose)
					)
					(else
						(Ok)
						(ego get iMarker)
						(theGame changeScore 50)
						(aMarker dispose)
						(Print "Great! A \"Magic\" marker.
							A girl never knows when she might need a little Magic
							in her life!")
					)
				)
			)

			((Said 'get/tips')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not egoIsPatti)
						(Print "Larry! That money belongs to Patti, not you!")
					)
					((not (TstFlg tipsIn450))
						(AlreadyTook)
					)
					((not aboveStool)
						(Print "You keep your tip jar above the treble end of your
							keyboard.")
					)
					(else
						(Ok)
						(ego get iMoney)
						((Inventory at iMoney) view ivTips)
						(Format ((Inventory at iMoney) name) "Tips")
						(theGame changeScore 25)
						(= dollars 43)
						(ClrFlg tipsIn450)			
						(aTips dispose)
						(Print "Let's see; did we have a good night tonight?
							Oh, well; a few bucks is better than nothing.
							Where you're going you're not going to need a lot of
							money anyway.")
					)
				)
			)

			((Said 'get, order, call/bartender, waitress, drink')
				(Print "You'll just have to wait until a waitress shows up.")
			)

			((Said '/bartender, waitress')
				(Print "You never can find one when you need one!")
			)

			((or	(Said 'give, put/money')
					(Said 'tip'))
				(cond
					(egoIsPatti
						(Print "You have no desire to tip yourself!")
					)
					((not (ego has iMoney))
						(DontHave)
					)
					((TstFlg tipsIn450)
						(Print "You really should save your money for another
							place.")
					)
					(else
						(Print "Save your money, Larry.
							This is one girl you'll not be able to purchase!")
					)
				)
			)

			((Said 'play/piano')
				(cond
					(egoIsPatti
						(Print "No, you've played this piano quite enough for
							one lifetime!")
					)
					((not (cast contains aPatti))
						(Print "On the scale of musical aptitude,
							you rank in the fourth percentile, nationally.")
						(if (== filthLevel RAUNCHY)
							(Print "(...just slightly below an elephant 
								passing gas!)" LOW)
						)
					)
					(else
						(Print "\"Say, Patti!\" you shout.
							\"Can you play `Melancholy Baby?'\"")
						;**	"Melancholy" spell-checked August 18, 1989
						(Print "\"Sure, buddy,\" she responds,
							\"here's one of my favorites.\"")
						(PattiScript changeState playPiano)
					)
				)
			)

			((Said 'listen')
				(if pattiAtPiano 
					(Print "She sounds a lot like a hip Bobby Short!")
				else
					(Print "There's nothing more dull than a piano bar 
						without a piano player!")
				)
			)

			((Said 'look<under')
				(Print "You look under it and see its bottom.")
			)

			((Said 'look>')
				(cond
					((or	(Said '/blackboard')
							(and	(InRoom iMarker)
									(Said '/marker')))
						(cond
							((not egoIsPatti)
								(Print "The announcement board lists the drink
									specials of the day in a vivid florescent ink.
									You wonder how they get such vibrant, day-glo
									colors from a marking pen.")
							)
							((not (InRoom iMarker))
								(Print "The blackboard announces the beverage specials
									of the day... just above its now-empty tray.")
							)
							(else
								(Print "Why, look! Someone left the marking pen
									lying in the tray of the announcement board.")
							)
						)
					)

					((Said '/wall, ceiling, light')
						(Print "This room certainly has an unusual shape!
							Look at how the walls curve into the ceiling then
							into that spotlight.")
					)

					((Said '/bar')
						(if pattiAtPiano
							(Print "Patti looks beautiful, doesn't she?")
						else
							(Print "This is the dullest bar you've ever seen.")
							(Print "(And you've seen quite a few!)" LOW)
						)
					)

					((Said '/table')
						(Print "The tables here are usually crowded with Patti's
							fans.")
						(Print "(Perhaps they heard you coming!)" LOW)
					)

					((Said '/stool')
						(Print "The stools here are near the piano bar.")
					)

					((Said '/drink')
						(Print "You haven't ordered a drink yet.")
					)

					((and	(TstFlg tipsIn450)
							(Said '/jar'))
						(Print "On the end of the piano sits a large brandy
							snifter filled with money; tips donated by
							enthusiastic fans.")
					)

					((Said '/door')
						(Print "There is a door to the north marked
							\"Employees Only\" in addition to the way you entered.")
					)

					((Said '/piano, pianist')
						(cond
							(( < showroomState SRdone)
								(Print "The piano awaits the arrival of its new
									performer_-- Passionate Patti!")
							)
							((InRoom iKey)
								(Print "Look, Larry!
									It's that woman you've been waiting for_--
									Passionate Patti herself!!
									She looks even better than you expected.")
								(Print "(You wonder why she selected \"Passionate\"
									as her stage name.)" LOW)
							)
							((not egoIsPatti)
								(Print "She's not here, Larry.
									She's waiting for you upstairs.")
							)
							((TstFlg tipsIn450)
								(Print "It's your tip glass!
									Of course!
									You forgot all about it.")
							)
							(else
								(Print "This piano brings back fond memories for you.")
							)
						)
					)

					((Said '[/bar, room]')
						(cond
							(egoIsPatti
								(Print "This piano bar has been quite good to you,
									but then, you deserve it!")
							)
							(else
								(Print "Unusual walls rise to a sculptured ceiling
									that descends upon the stool before a grand piano.")
								(if pattiAtPiano
									(Print "Seated before you is none other than
										the queen of the keyboard,
										the irresistable instrumentalist of the ivories,
										the sultress of the Steinway,
										the babe of the Bosendorfer... Passionate Patti!")
								else
									(Print "This place is sure dead!")
								)
							)
						)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 1 1)

		(switch (= state newState)

			(intro
;**					(if demo
;**						(delay 3)
;**					)
			)

;**				(demo1
;**					(Print "As Passionate Patti, world famous pianist and
;**						lounge entertainer,
;**						you have the run of the island's casino (and male customers).
;**						This is where you perform nightly,
;**						holding court from your piano bar throne."
;**						BIG #dispose #time 10)
;**					(delay (+ 1 10))
;**				)
;**	
;**				(demo2
;**					(Print "As Patti shows us the casino, we'll remind you that
;**						you needn't have played the earlier Leisure Suit Larry games
;**						in order to enjoy Larry 3."
;**						BIG #dispose #time 12)
;**					(delay 7)
;**				)
;**	
;**				(demo2a
;**					(ego
;**						setMotion		MoveTo 29 188 self,
;**					)
;**				)
;**	
;**				(demo3
;**					(self cue)
;**				)
;**	
;**				(demo4
;**					(cls)
;**					(curRoom newRoom 416)
;**				)


			;**	Move L to his stool and sit him down.

			(sitYourAssDown
				(HandsOff)
				(Ok)
				(ego
					ignoreActors,
					illegalBits			0,
				)
				(cond
					(aboveStool
						(self changeState walkEast)
					)
					(besideStool
						(self changeState walkNorth)
					)
					(else							;**	head west, first
						(ego
							setMotion		MoveTo 118 (ego y) self,
						)
					)
				)
			)

			(walkNorth
				(ego
					setMotion		MoveTo (ego x) 113 self,
				)
			)

			(walkEast
				(ego
					setMotion		MoveTo 134 113 self
				)
			)

			(okSit
				(ego
					posn				sittingPosn,
					view				vLroom,
					loop				lLsitting,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(sitting
				(if (!= egoState DOING_IT)
					(User canInput TRUE)
					(= egoState SITTING)
				)
				(ego
					loop				(Random lLlookingLeft lLlookingRight),
					cel				0,
					cycleSpeed		(Random 0 2),
					setCycle			EndLoop ,
				)
				(-- state)
				(delay (Random 3 6))
			)


			;**	STAND UP

			(standUp
				(Ok)
				(HandsOff)
				(= cycles 0)
				(delay 0)
				(ego
					setLoop			lLsitting,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(doneStanding
				(ego
					posn				134 113,
				)
				(NormalEgo facingUp)
				(= egoState NORMAL)
			)


			;**	Handle L looking at P

			(goingTo455
				(HandsOff)
				(Ok)
				(= egoState GOING_TO_BIG_FACE)
				(if (not (TstFlg seenPatti))
					(Printf "\"Hello, beautiful,\" you say to the pianist.
						%s" laffer)
					(delay 3)
				else
					(self cue)
				)
			)

			(changeRooms
				(if (not (TstFlg seenPatti))
					(SetFlg seenPatti)
					(theGame changeScore 5)
					(Print "\"And hello to you, Larry,\" replies Passionate Patti.
						\"I'm the girl of your dreams, Passionate Patti!\"")
				)
				(curRoom newRoom 455)
			)
		)
	)
)

(instance aElvis of Prop
	(properties
		view 				vDrinkers
      loop				lElvis
		x					150
		y					145
	)
	(method (init)
		(super init)
		(self
			setScript		ElvisScript,
		)
	)
)
(instance ElvisScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look/man, elvis')
				(Print "Why, he looks just like The King!")
			)

			((Said 'talk/elvis')
				(Print "He looks like he doesn't want to be disturbed.")
			)

			((Said '/elvis')
				(Print "So this is where he's been hiding!")
				(self changeState winkForRogerElvis)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 3 3)

		(switch (= state newState)

			(ElvisInit
				(aElvis
					loop				lElvis,
					setCel			0,
				)
				(= cycles (Random 22 111))
			)

			(doSomethingElvis
				(if (not (Random 0 3))
					(aElvis
						loop			(Random lElvis lElvisLookingLeft),
						cel			0,
						setCycle		EndLoop ,
					)
				else
					(= state readyToStartOver)
				)
				(= cycles (Random 22 55))
			)

			(returnToElvis
				(aElvis
					setCycle			BegLoop self,
				)
				(= state readyToStartOver)
			)

			;**	Wink for Roger, Elvis!

			(winkForRogerElvis
				(aElvis
					loop				lElvisWinking,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 22)
				(= state doSomethingElvis)
			)
		)
	)
)

(instance aRoger of Prop
	(properties
		view 				vDrinkers
      loop				lRoger
		x					144
		y					133
	)
	(method (init)
		(super init)
		(self
			setScript		RogerScript,
		)
	)
)
(instance RogerScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look/man, roger')
				(Print "Hey, look! It's Roger Wilco.")
			)

			((Said 'talk/roger, man')
				(Print "He looks like he doesn't want to be disturbed.")
			)

			((Said '/roger')
				(Print "\"So what are you doing here without those Two Guys?\"
					you ask him.")
				(Print "\"What two guys?\" he replies.")
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 4 4)

		(switch (= state newState)

			(RogerInit
				(aRoger
					loop				lRoger,
					setCel			0,
				)
				(delay (Random 5 10))
			)

			(doSomethingRoger
				(if (not (Random 0 2))
					(aRoger
						loop			(Random lRoger lRogerLookingLeft),
						setCycle		EndLoop ,
					)
				else
					(= state readyToStartOver)
				)
				(= cycles (Random 22 55))
			)

			(returnToNormalRoger
				(aRoger
					setCycle			BegLoop self,
				)
				(= state readyToStartOver)
			)
		)
	)
)

(instance atpPiano of PicView
	(properties
		view					vRoom
;		loop					lMisc
;		cel					cPiano
		x						143
		y						116
		priority				8
		signal				ignrAct
	)
)
(instance aTips of View
	(properties
		view					vRoom
;		loop					lMisc
		cel					cTips
		x						150
		y						91
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			9,
			stopUpd,
		)
	)
)
(instance atpBlackboard1 of PicView
	(properties
		view					vRoom
;		loop					lMisc
		cel					cBoard1
		x						36
		y						153
		priority				10
	)
)
(instance atpBlackboard2 of PicView
	(properties
		view					vRoom
;		loop					lMisc
		cel					cBoard2
		x						36
		y						153
		priority				10
	)
)
(instance aMarker of Prop
	(properties
		view					vRoom
		loop					lMarker
		x						29
		y						145
		cycleSpeed			2
	)
	(method (init)
		(super init)
		(self
			setPri			11,
			setCycle			Forward,
		)
	)
)
(instance atpChair1 of PicView
	(properties
		view					vRoom
;		loop					lMisc
		cel					cStool
		x						137
		y						122
		priority				8
	)
)
(instance atpChair2 of PicView
	(properties
		view					vRoom
;		loop					lMisc
		cel					cStool
		x						144
		y						133
		priority				9
	)
)
(instance atpChair3 of PicView
	(properties
		view					vRoom
;		loop					lMisc
		cel					cStool
		x						150
		y						145
		priority				10
	)
)
(instance atpChair4 of PicView
	(properties
		view					vRoom
;		loop					lMisc
		cel					cStool
		x						170
		y						155
		priority				11
	)
)
(instance atpChair5 of PicView
	(properties
		view					vRoom
;		loop					lMisc
		cel					cStool
		x						192
		y						155
		priority				11
	)
)

(instance aPatti of Actor
	(properties
		view 			vProom
;		loop			lPtalking
		x				166
		y				84
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			illegalBits		0,
			setPri			white,
			setScript		PattiScript,
		)
	)
)
(instance PattiScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((super handleEvent event))

			((Said 'take, make, (ask<about), play/song, request')
				(music			fade)
				(= request 
					(switch (Random 1 16)
						( 1	sFanfare)	
						( 2	sTitle)
						( 3	sVoyeur)	
						( 4	sTawni)	
						( 5	sSuzi)	
						( 6	sStripMusic)
						( 7	sDale)
						( 8	sBambi)
						( 9	sFatCityRegion)
						( 0	sCherriDancing)	
						(11	sCherri)	
						(12	sPbamboo)
						(13	sWhitewaterRafting)
						(14	sJungleRegion)
						(15	sFeralPig)
						(else	sKeystone)
					)
				)
				(Printf "\"Ok,\" says Patti, \"how about a selection from that
					hot new Sierra adventure game `Leisure Suit Larry 3?'
					Here's the hauntingly-beautiful LSL3 `%s\ Theme.'\"" 
					(switch request
						(sFanfare				{Sierra})	
						(sTitle					{Title})
						(sVoyeur					{Binocular})	
						(sTawni					{Tawni's})	
						(sSuzi					{Dewey, Cheatem and Howe})	
						(sStripMusic			{Dale Exotic Dance})
						(sDale					{Dale's})
						(sBambi					{Bambi's})
						(sFatCityRegion		{Fat City})
						(sCherriDancing		{Cherri's Dance})	
						(sCherri					{Cherri's})	
						(sPbamboo				{Bamboo})
						(sWhitewaterRafting	{Whitewater Rafting})
						(sJungleRegion			{Nontoonyt Jungle})
						(sFeralPig				{Feral Pig})
						(else						{Larry Gets Crabs})
					)
				)
				(self changeState playPiano)
			)

			((Said 'talk[/patti]')
				(cond
					(egoIsPatti
						(Print "No one can hear you, Patti.")
					)
					(else
						(Print "Before speaking to a woman,
							it is advisable to look at her, Larry.")
					)
				)
			)

			((Said 'look/patti')
				(cond
					((!= egoState SITTING)
						(Print "Why not have a seat first, Larry?")
					)
					(else
						(RoomScript changeState goingTo455)
					)
				)
			)

			((Said '/patti')
				(Print "Perhaps you should establish eye contact.")
			)

		)
	)


	(method (changeState newState &tmp howMany)
		(ShowState self newState 2 2)

		(switch (= state newState)

			(PattiInit
			)
				
			(playPiano
				(delay 0)
				(aPatti
					view				vProom,
					viewer			pianoCycler,
				)
				(if request
					(= howMany 1)
				else
					(= request (Random sPattiPiano1 sPattiPiano4))
					(= howMany (Random 1 3))
				)
				(music
					stop,
					number			request,
					loop				howMany,
					play				self,
				)
			)

			(oneMoreTime
				(music
					number			request,
					loop				1,
					play,
				)
				(= request 0)
				(delay (Random 3 22))
			)

			(stopPlayingPiano
				(music				fade)
				(aPatti
					viewer			FALSE,
					loop				lPtalking,
					setCel			0,
				)
				(delay 7)
			)

			(howAbout
				(Print "\"Here's another song you might enjoy,\" says Patti.")
				(self changeState playPiano)
			)

			(neverPlayAgain
			)


			;**		Here's where Patti leaves the piano and walks out to go
			;**	upstairs to wait for Larry...

			(leave
				(music
					stop,
					number			sCasinoRegion,
					loop				bgMusicLoops,
					play	,
				)
				(delay 3)
			)

			(atLarry
				(aPatti
					setMotion		MoveTo 145 112 self,
				)
			)

			(soLongLarry
				(Print "\"Here, Larry, take the key to my suite,\"
					says Patti.
					\"I'll slip out the back way and use the service elevator.
					Just wait a few minutes before you come upstairs, 
					as I'd like to slip into something more comfortable...\"")
				(Print "\"...like your flowers!\"")
				(ego get iKey)
				(PutInRoom iBottle 340)
				(theGame changeScore 25)
				(aPatti
					setLoop			RELEASE,
					setMotion		MoveTo 168 112 self,
				)
			)

			(centeredOnDoor
				(Print "\"Oh, and remember Larry: 
					I refuse to make love to a man without something to 
					drink first!\" she says over her shoulder.
					\"No offense, of course.\"")
				(aPatti
					setMotion		MoveTo 168 46 self,
				)
			)

			(outtaHere
				(Print "(None taken, you suppose.)" LOW)
				(aPatti	dispose)
				(User canInput TRUE)
 				(= egoState SITTING)
			)
		)
	)
)

(instance pianoCycler of Code
	(method (doit)
		(if (not (Random 0 9))
			(aPatti cycleSpeed (Random 0 1))
		)
		(if (not (Random 0 5))
			(aPatti loop (Random 0 3))
		)
	)
)
(instance blockPatti of Block
	(properties
		top				 90
		bottom			111
		left				157
		right				177
	)
)
