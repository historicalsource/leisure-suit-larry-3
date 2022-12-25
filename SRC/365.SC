;**
;**	Logics for room 365 -- Tanning Booth
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 17, 1989
;**


(script#				365)

(public
	rm365				0
)


(procedure
	PrintBambi
	PrintLarry
)


(define	vBambi&Larry				365)
	(define	lBottom				0)
	(define	lTop					1)
	(define	lBambi				2)
	(define  lLarry				3)
		(define cInLight		0)
		(define cInDark		1)
	(define	lDoor					4)

(define	vL&BdoingIt					366)
	(define	lOntoBed				0)
	(define	lHumping				1)
	(define	lFeet					2)
	(define	lHeads				3)

;(define	vBambi						393)
	(define	lRightLight			0)
	(define	lRightDark			1)
	(define  lDownLight			2)
	(define	lUsingCard 			3)
	(define	lWaitingByBed		4)

;(define	vLdarkHunk					725)

(define  flashPicture				366)
;(define  blackPicture				099)


;(define	sForeplay					008)	
;(define	sInterrupted				009)	
;(define	sKeystone					261)
(define	sTanMachine					365)
;(define	sDoorClosing				011)


(define	DoorX							118)
(define	DoorY							 62)
(define	BedX							199)
(define	BedY							118)
(define	LidX							235)
(define	LidY							 83)


(enum												;**	RoomScript state values
	intro
	closeDoor
	dimLight
	how'sThis
	moveBambi
	BambiInPlace
	comeOnOver
	smile
	startLarry
	LarryInPlace
	mountUp
	doingIt
	noPain
	comeDown
	dropIt
	flashOfLight
	seeingLights
	getItUp
	heyBambi
	yourProblem
	it'sStuck
	noBambi
	fadeToBlack
	sheSlides
	changeRooms
)


(instance rm365 of Room
	(properties
		picture		365
	)

	(method (init)
		(Load VIEW vL&BdoingIt)
		(Load VIEW vLdarkHunk)

		(Load PICTURE flashPicture)
		(Load PICTURE blackPicture)

		(Load SOUND sDoorClosing)
		(Load SOUND sTanMachine)
		(Load SOUND sForeplay)
		(Load SOUND sInterrupted)
		(Load SOUND sKeystone)

		(super init)

		(SetFlg hadBambi)

		(HandsOff)

		(StatusLine	disable)
		(TheMenuBar	
			hide,
			state 			FALSE,
		)

		(aDoor   			init)
		(aLid  				init)
		(aBambi 				init)

		(addToPics
			add 				atpBed,
			doit
		)

		(self
			setScript 		RoomScript
		)

		(ego
			view				vBambi&Larry,
			loop				lLarry,
			cel				cInLight,
			setCycle			Walk,
			illegalBits		0,
			posn				115 112,
			ignoreActors,
			init,
		)
	)
)


(instance RoomScript of Script
	(method (changeState newState)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
				(delay 3)
			)

			(closeDoor
				(aDoor
					setCycle			EndLoop self
				)
			)

			(dimLight
				(music				stop)
				(soundFX
					number			sDoorClosing,
					loop				1 ,
					play ,			
				)
				(aBambi
					cel				cInDark,
				)
				(ego
					cel				cInDark,
				)
				(aDoor	 			stopUpd)
				(delay 3)
			)

			(how'sThis
				(PrintBambi "\"Here's a nice quiet spot where I'm sure 
					we won't be disturbed.
					This old tanning machine has been broken since the
					late eighties.\"")
				(delay 3)
			)

			(moveBambi
				(aBambi
					view				vBambi,
					setLoop			lRightDark,
					setMotion		MoveTo 180 110 self
				)
			)

			(BambiInPlace
				(aBambi
					setLoop			lWaitingByBed,
					cel				0,
					stopUpd,
				)
				(delay 3)
			)

			(comeOnOver
				(music
					number			sForeplay,
					loop				FOREVER,
					play
				)
				(PrintBambi "\"So?\" she asks,
					\"why don't you step over here to my casting couch and 
					I'll audition you for a role in my new video!\"")
				(delay 3)
			)

			(smile
				(Print "(Perhaps a \"roll\" would be more like it!)" LOW)
				(delay 3)
			)

			(startLarry
				(ego
					view				vLdarkHunk,
					setLoop			facingRight,
					setMotion		MoveTo 166 110 self
				)
			)

			(LarryInPlace
				(PrintLarry "\"Bambi, I may not be able to keep up with you 
					aerobically,
					but remember: practice makes perfect!\"")
				(delay 3)
			)

			(mountUp
				(aBambi				hide)
				(ego
					view				vL&BdoingIt,
					setLoop			lOntoBed,
					posn				(aBambi x) (- (aBambi y) 20),
					setCycle			EndLoop self,
				)
			)

			(doingIt
				(ego
					viewer			egoHumpCycler,
					posn 				201 94,
					setLoop			lHumping,
					setCycle			Forward,
				)
				(delay 7)
			)

			(noPain
				(PrintBambi "\"You know, Larry: no pain, no gain!\"")
				(delay 5)
			)

			(comeDown
				(Print "Your energetic pre-aerobic warmup loosens the stuck lid
					of the defective tanning machine...")
				(= cycles 11)
			)

			(dropIt
				(soundFX
					number			sTanMachine,
					loop				1,
					play,
				)
				(aLid
					setCycle			EndLoop self,
				)
			)

			(flashOfLight
				(curRoom drawPic flashPicture WIPELEFT)
				(aLid					dispose)
				(aDoor				dispose)
				(ego
					viewer			egoHumpCycler,
					loop 				lFeet,
					setPri	 		12,
					cel				0,
					setCycle			Forward,
;**						stopUpd,
				)
				(aBambi
					posn 				187 75,
					view 				vL&BdoingIt,
					loop 				lHeads,
					setPri	 		4,
					cel				0,
					setCycle			Forward,
					show,
		;**				stopUpd,
				)
				(music
					number			sKeystone,
					loop				FOREVER,
					play ,			
				)
				(delay 5)
			)

			(seeingLights
				(PrintBambi "\"Wow, Larry! You're the first man who's ever made
					me see lights!!\"")
				(delay 5)
			)

			(getItUp
				(Print "The brilliant glow of 1500 watts of ultraviolet-B
					heats your back as quickly as it cramps your style.")
				(delay 3)
			)

			(heyBambi
				(PrintLarry "\"Hey, Bambi,\" you cry, struggling to raise the lid,
					\"how do you get this thing up?\"")
				(delay 3)
			)

			(yourProblem
				(PrintBambi "\"That's YOUR problem, Larry.
					I'd just like to raise the lid of this tanning booth!\"")
				(ego
					viewer			FALSE,
					stopUpd,
				)
				(aBambi
					stopUpd,
				)
				(delay 3)
			)

			(it'sStuck
				(PrintBambi "\"Larry, help! It's stuck!!\"")
				(delay 3)
			)

			(noBambi
				(PrintLarry "\"No, it's not; I'm just having trouble moving
					with this lid on my back!\"")
				(delay 3)
			)

			(fadeToBlack
				(music				fade)
				(curRoom drawPic blackPicture IRISIN)
				(cast eachElementDo #hide)
				(delay 3)
			)

			(sheSlides
				(Print "Bambi slips out of the tanning machine,
					out the door, and out of your life,
					leaving you to ponder yet another of life's great 
					mysteries...")
				(music
					number			sInterrupted,
					loop				1,
					play,
				)
				(Print "...why is it so damned hard to get an even tan in a machine!")
				(delay 3)
			)

			(changeRooms
				(systemWindow
					color 		textColor,
					back			backgroundColor,
				)
				(= egoState NORMAL)
				(curRoom newRoom 360)
			)
		)
	)
)

(instance aBambi of Actor
	(properties
		view					vBambi&Larry
		loop					lBambi
		cel					cInLight
		x						124
		y						108
	)
	(method (init)
		(super init)
		(self
			illegalBits		0,
			setPri			7,
			setCycle			Walk,
			ignoreActors,
		)
	)
)
(instance aDoor of Prop
	(properties
		view				vBambi&Larry
		x	 				DoorX
		y					DoorY
		loop				lDoor
	)
	(method (init)
		(super init)
		(self
			setPri		green,
			ignoreActors,
		)
	)
)
(instance aLid of Prop
	(properties
		view				vBambi&Larry
		x	 				LidX
		y					LidY
		loop				lTop
	)
	(method (init)
		(super init)
		(self
			setPri		ltRed,
			ignoreActors,
		)
	)
)
(instance atpBed of PicView
	(properties
		view				vBambi&Larry
		x	 				BedX
		y					BedY
		loop				lBottom
		priority			red
		signal			ignrAct
	)
)

(instance humpCycler of Code
	(method (doit &tmp s)
		(cond
			((<= filthLevel RISQUE)
				(ego stopUpd)
				(aBambi stopUpd)
			)
			((not (Random 0 9))
				(= s  (Random 0 5))
				(ego 		cycleSpeed s)
				(aBambi	cycleSpeed s)
			)
		)
	)
)
(instance egoHumpCycler of Code
	(method (doit &tmp cs)
		(cond
			((<= filthLevel RISQUE)
				(ego stopUpd)
				(aBambi stopUpd)
			)
			((not (Random 0 9))
				(= cs (Random 0 5))
				(ego cycleSpeed cs)
				(aBambi cycleSpeed cs)
			)
		)
	)
)
(procedure (PrintBambi &tmp)
	(systemWindow
		color 		ltRed,
		back			ltBlack,
	)
	(Print &rest
		#at 			195 20
		#title		{Bambi says}
		#width 		115
	)
	(systemWindow
		color 		black,
		back			ltBlack,
	)
)
(procedure (PrintLarry &tmp)
	(systemWindow
		color 		ltBlue,
		back			ltBlack,
	)
	(Print &rest
		#at 			10 111
		#title		{You say}
		#width 		150
	)
	(systemWindow
		color 		black,
		back			ltBlack,
	)
)

