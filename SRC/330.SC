;**
;**	Logics for room 330 -- Chip 'N' Dale's
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 23, 1989
;**


(script#				330)

(public
	rm330				0
)


(define	vRoom							330)
	(define	lCurtain				0)
	(define	lChairs				1)
		(define	cPattiChair	0)
		(define	cDaleChair	1)
	(define	lExtra1				2)

(define	vLoverhead					331)
(define	vPoverhead					332)
	(define	lSitting				4)
	(define	lClapping			5)			;**	applauding
	(define	lCrossingLeg		6)

(define	vDaleStripping				333)
	(define	lEntrance			0)
	(define	lUndress				1)
	(define	lTossClothes		2)
		(define	cNoClothes	6)
	(define	lGoingDown			3)
	(define	lGrind				4)
	(define	lRussian				5)
	(define	lSplits				6)
	(define	lClothes				7)

(define	vGirlStripping				334)

(define	vPattiPanties				336)
	(define	lRemovingPanties	0)
	(define	lWindUp				1)
	(define	lThrowing			2)
	(define	lPantiesFlying		3)

(define	vDaleOverhead				337)


;(define	sTimpaniRoll				010)
;(define	sStripMusic					330)	
(define	sChaser						331)	
(define	sNonStripMusic				332)	


(define	nearChair				(& (ego onControl) cBlue))
(define	nearDaleChair			(& (ego onControl) cGreen))


(define	chairX						281)
(define	chairY						140)
(define	DaleChairX 					283)
(define	DaleChairY 					121)
(define	PANTIES_X					 42)
(define	PANTIES_Y					 96)
(define	TIME_BEFORE_DALE_LEAVES	 11)


(synonyms
	(man dale stripper )
)


(enum												;**	RoomScript state values
	intro
	gotoChair
	atChair
	sitting
	fidgetClap
	fidgetPause
	crossLegs
	keepEmTight
	fidget3
	getUp
	moveOver
	standing
	popEm
	propeller
	fasterPatti
	takeMe
	throwEm
	pantsDone
	goingToDale
	changeRooms
)


(enum												;**	DaleScript state values
	DaleInit
	comeOnInDale
	ladiesAndLadies
	curtainOpen
	dancing
	curtainClosed
	windUp
	throwClothes
	pauseN
	goDown
	grindIt
	moveDaleOver
	leaving
	grindSomeMore
	aintHeGrand
	openCurtain
	headWest
	grabYourPanties
	behindCurtain
	waitingToReturn
	Dale'sBack
	nearPtable
	decisionTime
	DaleAtChair
	DaleSitting
	gottaGo
	walkOff
	DaleGone
)


(enum												;**	drinkerScript state values
	drinkerInit
	drinkerStart
	smallDelay
	drinkerMiddle
	drinkerDone
)


(enum 1											;**	DaleState values
	ENTERING
	DANCING
	LEAVING_STAGE
	D_BACKSTAGE
	IN_AUDIENCE
	SITTING_WITH_PATTI
	GONE
)


(local
	clothesOnTable
	seenDaleDance
	DaleState
	currentDrinker
	humpCount
	sitWithPatti
)


(instance rm330 of Room
	(properties
		picture		330
		east			230
	)


	(method (init)
		(SetFlg beenIn330)
		(= egoState OVERHEAD)

		(Load VIEW vRoom)
		(Load VIEW vDaleStripping)
		(Load VIEW vPoverhead)
		(Load VIEW vDaleOverhead)
		(Load VIEW vPattiPanties)
		(Load VIEW iPanties)

		(Load SOUND sStripMusic)
		(Load SOUND sChaser)
		(Load SOUND sNonStripMusic)
		(Load SOUND sTimpaniRoll)

		(Load SCRIPT JUMP)
		(Load SCRIPT REVERSE)

		(super init)

		(addToPics
			add 				atpPchair,
			add 				atpDchair,
			doit
		)

		(aDale				init)
		(aClothes			init)
		(aDrinker1			init)
		(aDrinker2			init)
		(aDrinker3			init)
		(aDrinker4			init)
		(aCurtain			init)
		(aPanties			init)

		(self
			setScript 		RoomScript
		)

		(if (== prevRoomNum 335)
			(HandsOff)
			(ego
			 	view				vPoverhead,
				setLoop			lSitting,
				setCel			LastCel,
			 	posn				chairX chairY,
				illegalBits		0,
			 	init,
			)
			(RoomScript changeState sitting)

			(aDale
				view				vDaleOverhead,
				setLoop			lSitting,
				setCel			LastCel,
				posn				DaleChairX DaleChairY,
				cycleSpeed		1,
			)
			(DaleScript changeState DaleSitting)
		else
			(NormalEgo)
			(ego
			 	view				vPoverhead,
			 	loop				facingWest,
			 	posn				317 129,
				baseSetter		squareBase,
			 	init,
			)
		)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(if DaleState
			(cond
				((Said 'touch, get, grab, feel/stripper')
					(Print "You'd love to, wouldn't you?")
				)

				((Said 'clap')
					(Print "You applaud uproariously.")
					(if (== egoState SITTING)
						(self changeState fidgetClap)
					)
				)

				((Said '(take<off), remove/panties')
					(Print "An excellent idea! But what would you do with them?")
				)

				((Said 'give, throw/panties>')
					(cond
						((not (ego has iPanties))
							(DontHave)
						)
						((Said '/[/noword]')
							(Print "That's a good idea! But to whom?")
						)
						((not (Said '//stage, dale'))
							(Print "No, they wouldn't want your panties.")
						)
						((!= DaleState DANCING)
							(Print "A good idea!
								But Dale is not dancing at the moment.")
						)
						(else
							(self changeState popEm)
						)
					)
					(event claimed TRUE)
				)

				((Said 'look/stripper, eye')
					(cond
						((== DaleState FALSE)
							(Print "Where?")
						)
						((< DaleState SITTING_WITH_PATTI)
							(Print "Wow! He looks exactly like a young Tom Jones!
								The women here are going wild!")
							(Print "(The men look terminally bored.)" LOW)
						)
						((> DaleState SITTING_WITH_PATTI)
							(Print "He's gone, Patti.")
						)
						((!= egoState SITTING)
							(Print "Sit down beside him first.")
						)
						(else
							(self changeState goingToDale)
						)
					)
				)

				((Said 'talk/stripper')
					(switch DaleState
						(DANCING
							(Print "He's really a hot dancer, Patti!
								Back in your B.L. period (Before Larry),
								you would have been instantly intensely 
								interested in a man like him.
								Even now, something tells you to attract his
								attention.")
						)
						(LEAVING_STAGE
							(Print "He can't hear you for the roar of the crowd.")
						)
						(D_BACKSTAGE
							(Print "He's backstage now... resting up.")
						)
						(IN_AUDIENCE
							(Print "\"Oh, Dale,\" you coo,
								\"would you like to join me?\"")
							(= sitWithPatti TRUE)
						)
						(ENTERING
							(Print "He can't hear you for the roar of the crowd.")
						)
						(SITTING_WITH_PATTI
							(Print "You've got him sitting here with you;
								why don't you try looking into his eyes?")
						)
						(else
							(Print "He's gone for good, Patti;
								you've missed him!")
						)
					)
				)

				((Said 'talk')
					(Print "The people at the tables are uninterested in
						conversing with you Patti.
						They're here to see the show.")
				)

				((and (== DaleState SITTING_WITH_PATTI)
						(or	(Said 'lick, eat, suck/dale')
								(Said 'rub, fondle, feel/dale')))
					(Print "Ok.")
				)
			)
		)


		(cond
			((and clothesOnTable
					(Said 'get, (up<pick)/clothes'))
				(Print "Just leave them there.
					You have no interest in cross-dressing.")
			)

			((Said 'get, order/drink, glass')
				(Print "You didn't come in here to drink, Patti!")
			)

			((or	(Said 'go, climb, (get<on)/stage, table, chair')
					(Said 'whistle, yell'))
				(Print "You're too sophisticated to lower yourself like that!")
			)

			((Said 'sit')
				(cond
					(nearDaleChair
						(Print "This chair not only faces the wrong way,
							but has a \"Reserved\" sign on it.
							However, the chair beside it is unoccupied.")
					)
					((not nearChair)
						(NotClose)
					)
					((== egoState SITTING)
						(YouAre)
					)
					((!= egoState OVERHEAD)
						(NotNow)
					)
					(else
						(Ok)
						(self changeState gotoChair)
					)
				)
			)

			((or	(Said 'rise, (get<off), (get<up), (stand<up)')
					(Said 'leave/chair'))
				(cond
					((== egoState OVERHEAD)
						(YouAre)
					)
					((!= egoState SITTING)
						(NotNow)
					)
					((== DaleState SITTING_WITH_PATTI)
						(Print "It would be rude for you to ignore your guest,
							Patti!")
					)
					(else
						(self changeState getUp)
					)
				)
			)

			((Said 'throw/money, tip, dollar')
				(Print "That's not what you should throw in this place.")
			)

			((Said 'look, feel<under/table, chair')
				(Print "You wouldn't want to!")
			)

			((Said 'look>')
				(cond
					((and clothesOnTable
							(Said '/clothes, table'))
						(Print "Perhaps Dale finds you interesting.
							Maybe his clothes are a clue...")
					)

					((Said '/stage')
						(Print "That's why people come here_--
							to look at the stage!")
					)

					((Said '/chair')
						(Print "There are only two chairs available here.")
						(if (== egoState SITTING)
							(Print "You're sitting in one of them.")
						)
						(if (== DaleState SITTING_WITH_PATTI)
							(Print "Dale is sitting beside you!")
							(Print "(And you're looking at his chair?)" LOW)
						)
					)

					((Said '/light')
						(Print "The spotlight is aimed directly at the stage.")
					)

					((Said '/curtain')
						(if (aCurtain cel)
							(Print "Through that curtain walks someone who may
								be helpful to your quest, Patti!")
						else
							(Print "It successfully prevents you from
								looking backstage.")
						)
					)

					((Said '/table, woman, man, glass, drink, people')
						(Print "Unlike you,
							the other people here are happy and enjoying themselves;
							not worried about finding their lover like you.")
					)

					((Said '[/room]')
						(Print "What a nasty establishment this is!")
						(Print "(You love it!)" LOW)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**		This section of script handles ego sitting,
			;**	fidgeting & standing up again.

			(gotoChair
				(HandsOff)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion	 	MoveTo chairX chairY self,
				)
			)

			(atChair
				(ego
					setLoop		lSitting,
					setPri		cyan,
					setCel		0,
					setCycle		EndLoop self,
				)
			)

			(sitting
				(User canInput TRUE)
				(= egoState SITTING)
				(if seenDaleDance
					(= cycles 0)
					(delay 3)
				else
					(= seenDaleDance TRUE)
					(DaleScript changeState comeOnInDale)
				)
			)

			(fidgetClap
				(if (== DaleState DANCING)	;**	applaud if he's dancing
					(ego
						loop 			lClapping,
						setCycle		Forward,
					)
					(= cycles (Random 11 33))
				else
					(self changeState crossLegs)
				)
			)

			(fidgetPause
				(ego
					setLoop		lSitting,
					setCel		LastCel,
				)
				(= cycles (Random 11 33))
			)

			(crossLegs
				(ego
					setLoop		lCrossingLeg,
					cel			0,
					setCycle		EndLoop self,
				)
			)

			(keepEmTight
				(= cycles (Random 33 66))
			)

			(fidget3
				(ego
					setCycle		BegLoop self,
				)
				(= state (- fidgetClap 1))	;**	When done, start clapping again.
			)


			;**	We only get here by direct cue from a Said statement.

			(getUp
				(HandsOff)
				(delay 0)						;**	Kill any loose timers.
				(= cycles 0)
				(ego
					view 				vPoverhead,
					setLoop			lSitting,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(moveOver
				(ego
					setCycle			Walk,
					setLoop			facingWest,
					setMotion		MoveTo 282 132 self,
				)
			)

			(standing
				(= egoState OVERHEAD)
				(NormalEgo facingWest vPoverhead)
				(ego
					baseSetter		squareBase,
				)
			)


			;**	Here's where Patti pops her panties & tosses 'em at Dale...

			(popEm
				(Ok)
				(Print "You slip off your \"Saturday Night Specials\"
					and readjust your pantyhose_--
					all in one smooth movement!")
				(delay 0)						;**	Kill any loose timers.
				(= cycles 0)
				(HandsOff)
				(ego
					view			vPattiPanties,
					setLoop		lRemovingPanties,
					cel			0,
					cycleSpeed	1,
					setCycle		EndLoop self,
				)
			)

 			(propeller
				(ego
					setLoop		lWindUp,
					cel			0,
					setCycle		Forward,
				)
				(= cycles 15)
			)

			(fasterPatti
				(ego
					cycleSpeed	0,
				)
				(= cycles 15)
			)

			(takeMe
				(= cycles 10)
			)

			(throwEm
				(Print "\"Hey, Dale,\" you cry,
					\"these are for you!\""
					#icon iPanties 0 0
					HIGH
				)
				(ego
					cycleSpeed	1,
					setLoop		lThrowing,
					cel			0,
					setCycle		EndLoop,
				)
				(PutInRoom iPanties)
				(aPanties
					posn			(ego x) (ego y),
					setMotion	JumpTo PANTIES_X PANTIES_Y self,
				)
			)

			(pantsDone
				(Print "You shriek,
					\"Take me, big boy. I'm yours!\"")
				(delay 0)
				(= cycles 0)
				(aPanties		stopUpd)
 				(theGame changeScore 100)
				(User canInput TRUE)
				(ego
					view 			vPoverhead,
				)
				(if (== egoState SITTING)
					(self changeState atChair)	;**	put your ass down, Patti
				else
					(NormalEgo facingWest vPoverhead)
					(ego
						baseSetter		squareBase,
					)
				)
			)


			;**	Patti wants to look at the Big Dale Face...

			(goingToDale
				(Ok)
				(HandsOff)
				(= egoState GOING_TO_BIG_FACE)
				(if (not (TstFlg seenDale))
					(Print "\"Hello, handsome,\" you tell the male stripper.
						\"My name is Patti,
						but you may call me Passionate!\"")
					(delay 3)
				else
					(self cue)
				)
			)

			(changeRooms
				(if (not (TstFlg seenDale))
					(SetFlg seenDale)
 					(theGame changeScore 1)
					(Print "\"Thanks, Patti,\" he responds,
						\"and you may call me Dale.\"")
				)
				(curRoom newRoom 335)
			)
		)
	)
)


(instance aDale of Actor
	(properties
		view					vDaleStripping
		x						-86
		y						86
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			setScript		DaleScript,
		)
	)
)
(instance DaleScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(DaleInit
			)


			;**	This is the script that brings out the male stripper for Patti

			(comeOnInDale
				(delay (Random 2 5))
			)

			(ladiesAndLadies
				(music
	 				number		sTimpaniRoll,
					loop			-1,
					play,
				)
				(Print "\"Ladies and ladies,\" says the voiceover announcer,
					\"Chip 'n' Dale's is proud to present,
					in person,
					tonight only,
					for one show only,
					the first,
					the original,
					the greatest,
					(the owner)
					...DALE!!!\"")
				(Print "(a smattering of applause)" LOW)
				(aCurtain
					setCycle			EndLoop self
				)
			)

			(curtainOpen
				(= oldGameSpeed (theGame setSpeed 6))
				(aCurtain			stopUpd)
				(music
					number			sStripMusic,
					loop				FOREVER,
					play,
				)
				(aDale
					setLoop			lEntrance,
					setMotion		MoveTo 89 86 self,
				)
				(= DaleState ENTERING)
			)

			(dancing
				(aCurtain
					setCycle			BegLoop self,
				)
				(aDale
					setLoop			lUndress,
					setCel			0,
				)
				(= DaleState DANCING)
			)

			(curtainClosed
				(aCurtain			stopUpd)
				(aDale
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(windUp
				(aDale
					setLoop			lTossClothes,
					setCel			0,
					setCycle			CycleTo cNoClothes ctForward self,
				)
			)

			(throwClothes
				(aDale
					setCycle			EndLoop,
				)
				(aClothes
					posn				105 83,
					setMotion		JumpTo 248 133 self
				)
			)

			(pauseN
				(aClothes			stopUpd)
				(= clothesOnTable TRUE)
				(= cycles 20)
			)

			(goDown
				(aDale
					cycleSpeed		1,
					setLoop			lGoingDown,
					setCel			0,
					setCycle			EndLoop self,
				)
				(= humpCount 0)
			)

			(grindIt
				(aDale
					setCycle			Forward,
					setLoop			lGrind,
				)
				(= cycles 22)
			)

			(moveDaleOver
				(if (> 3 (++ humpCount))
					(= state (- grindIt 1))
				)
				(aDale
					setLoop			lRussian,
					setMotion		MoveTo (Random 63 68) (Random 110 110),
				)
				(= cycles (Random 11 22))
			)

			(leaving
				(aDale
					setMotion		MoveTo 89 86 self
				)
			)

			(grindSomeMore
				(aDale
					setLoop			lSplits,
					cel				0,
					setCycle			EndLoop,
				)
				(music
					number			sChaser,
					loop				1,
					play,
				)
				(= cycles 33)
			)

			(aintHeGrand
				(Print "\"Ok, ladies! Let's really hear for him.\"" HIGH)
				(Print "\"Isn't he wonderful?!\"" HIGH)
				(= cycles (Random 11 44))
			)

			(openCurtain
				(aCurtain
					setCycle			EndLoop ,
				)
				(aDale
					setCycle			CycleTo 8 ctReverse,
				)
				(music
					number			sStripMusic,
					play,
				)
				(= cycles 22)
			)

			(headWest
				(aCurtain			stopUpd)
				(aDale
					cycleSpeed		0,
					setLoop			lRussian,
					setCycle			Forward,
					setMotion		MoveTo PANTIES_X 86 self
				)
				(= DaleState LEAVING_STAGE)
			)

			(grabYourPanties
				(if (InRoom iPanties 330)	;**	She tossed 'em.
					(aPanties		hide)		;**	They're awesome.
					((Inventory at iPanties) owner 335)	;**	Dale's got 'em.
				)
				(aDale
					setMotion		MoveTo -40 86 self
				)
			)

			(behindCurtain
				(aDale				stopUpd)
				(aCurtain
					setCycle			BegLoop
				)
				(music				fade)
				(= DaleState D_BACKSTAGE)
				(delay 5)
			)

			(waitingToReturn
				(theGame setSpeed oldGameSpeed)
				(aCurtain			dispose)
				(music
					stop,
					number			sNonStripMusic,
					loop				bgMusicLoops,
					play,
				)
				(delay (Random 7 15))
			)


			;**	Patti's still here; walk Dale out offstage

			(Dale'sBack
				(= DaleState IN_AUDIENCE)
				(aDale
					view				vDaleOverhead,
					setLoop			RELEASE,
					setCycle			Walk,
					ignoreActors	FALSE,
					illegalBits		0,
					posn				245 -10,
					baseSetter		squareBase,
					setMotion		MoveTo 316 129 self
				)
				(= cycles 20)
			)

			(nearPtable
				(Print "Patti, look! It's him!! Dale!!!
					He's here, walking through the audience!")
			)

			(decisionTime
				(if (or	sitWithPatti
							(InRoom iPanties 335))
					(= DaleState SITTING_WITH_PATTI)
					(aDale
						ignoreActors,
						illegalBits		0,
						setMotion		MoveTo DaleChairX DaleChairY self
					)
					(if (InRoom iPanties 335)
						(Print "\"Hey, that was you that threw that lovely little
							pair of panties at me, wasn't it?\" asks Dale.
							\"You look lonely. Would you like a little company?\"")
					else
						(Print "\"Say beautiful, aren't you that fabulous babe
							who's entertaining over at the casino?\" Dale asks you.
							\"It's a privilege for me even to sit with you!\"")
					)
				else
					(self changeState walkOff)
				)
			)

			(DaleAtChair
				(aDale
					posn				DaleChairX DaleChairY,
					cycleSpeed		1,
					setLoop			lSitting,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(DaleSitting
				(delay TIME_BEFORE_DALE_LEAVES)
			)


			;**	Here we wait for the timer to expire whence Dale leaves...

			(gottaGo
				(if (!= egoState SITTING)
					(delay 5)
					(-- state)					;**	try again soon
				else
					(Print "\"Well, babe, it's been my pleasure,\" says Dale,
						standing to leave.")
					(= DaleState IN_AUDIENCE)
					(aDale
						setCycle			BegLoop self,
					)
				)
			)

			(walkOff
;				(NormalActor aDale facingSouth)
				(aDale
					cycleSpeed		0,
					setStep			3 2,
					setCycle			Walk,
					ignoreActors	FALSE,
					illegalBits		0,
					setLoop			RELEASE,
					baseSetter		squareBase,
					setMotion		MoveTo 333 143 self,
				)
			)

			(DaleGone
				(= DaleState GONE)
				(aDale				dispose)
			)
		)
	)
)


(instance aDrinker1 of Prop
	(properties
		view			vRoom
		loop			2
		x				221
		y				8
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aDrinker2 of Prop
	(properties
		view			vRoom
		loop			3
		x				239
		y				44
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aDrinker3 of Prop
	(properties
		view			vRoom
		loop			4
		x				225
		y				77
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aDrinker4 of Prop
	(properties
		view			vRoom
		loop			5
		x				264
		y				166
	)
	(method (init)
		(super init)
		(self
			setScript	drinkerScript,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance drinkerScript of Script
	(method (changeState newState)		
		(switch (= state newState)

			(drinkerInit
				(delay (Random 2 5))
			)

			(drinkerStart
				(switch (Random 1 4)
					(1 (= currentDrinker aDrinker1))
					(2 (= currentDrinker aDrinker2))
					(3 (= currentDrinker aDrinker3))
					(4 (= currentDrinker aDrinker4))
				)
				(currentDrinker
					setCycle				EndLoop self,
				)
			)

			(smallDelay
				(= cycles (Random 15 29))
			)

			(drinkerMiddle
				(currentDrinker
					setCycle				BegLoop self,
				)
			)

			(drinkerDone
				(self changeState drinkerInit)
			)
		)
	)
)


(instance atpPchair of PicView
	(properties
		view			vRoom
		loop			1
;		cel			0
		x				281
		y				140
		priority		1
		signal		ignrAct
	)
)
(instance atpDchair of PicView
	(properties
		view			vRoom
		loop			1
		cel			1
		x				281
		y				121
		priority		1
		signal		ignrAct
	)
)

(instance aCurtain of Prop
	(properties
		view					vRoom
;		loop					lCurtain
;		cel					0
;		x						0
		y						88
		cycleSpeed			2
	)
	(method (init)
		(super init)
		(self
			setPri			3,
			ignoreActors,
		)
	)
)
(instance aPanties of Actor
	(properties
		view					vPattiPanties
;		cel					0
		x						283
		y						1142
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			setLoop			lPantiesFlying,
			setPri			15,
			setCycle			Forward,
			setStep			10 6,
			ignoreActors,
		)
	)
)

(instance aClothes of Actor
	(properties
		view					vDaleStripping
		x						105
		y						1083
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			setLoop			lClothes,
			setPri			15,
			setCycle			Forward,
			setStep			5 5,
			ignoreActors,
		)
	)
)

(instance squareBase of Code
	(method (doit whom)
		(whom brBottom		(+ (whom y) 5))
		(whom brTop			(- (whom brBottom) 9))
		(whom brLeft		(- (whom x) 8))
		(whom brRight		(+ (whom x) 8))
	)
)
