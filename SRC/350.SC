;**
;**	Logics for room 350 -- Natives, Inc. Reception
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 19, 1989
;**


(script#				350)

(public
	rm350				0
)


(define	vRoom							350)
	(define	lChairs				0)
	(define	lPins7&10			1)
	(define	lOtherPins			2)
	(define	lWaterfall			3)
	(define	lDoor					4)

(define	vLroom			(+ 1 vRoom))
	(define	lBowlingBall		0)
	(define	lUnwinding			1)

(define	vKen							352)

(define	vDave							353)
	(define	lDturningHead		0)
		(define cDheadLeft	 0)
		(define cDheadForward 2)
		(define cDheadRight	 4)
	(define	lDscratchHead		1)
	(define	lDleanBack			2)

;(define	sMuzak						032)
;(define	sDoorClosing				011)
(define	sBowling						350)
(define	sStrike						351)


(enum												;**	RoomScript state values
	intro
	goSeeKen
	nearDavesDesk
	goodMorning
	maybeSo
	okDave
	atDoor
	doorOpenDone
	changeToKensOffice
	cueTheKen
	larryAtPins
	timing
	split
	leave
	kenGone
	doorSlam
	larryLeaving
	changeRooms
)


(enum												;**	DaveScript state values
	DaveInit
	DaveStart
	itsDone
	watchLarry
)


(synonyms
	(man dave clerk )
)


(local
	DaveTalkCounter
)


(instance rm350 of Room
	(properties
		picture		350
		east			305
		west			355
	)


	(method (init)
		(super init)

		(addToPics
			add				atpChair1,
			add				atpChair2,
			add				atpChair3,
			add				atpChair4,
			add				atpTable1,
			add				atpTable2,
			add 				atpTenPin,
			doit
		)

		(self
			setScript 		RoomScript
		)

		(aDave			init)
		(aDoor			init)
		(aPins			init)
	
		(if (> machineSpeed pc8088)
			(aWaterfall		
				setCycle			Forward,
				isExtra			TRUE,
				init,
			)
		)

		(cond
			((== prevRoomNum 355)
				(HandsOff)
				(Load SOUND sBowling)
				(Load SOUND sStrike)
				(Load SOUND sDoorClosing)
				(Load PICTURE blackPicture)
				(aKen
					ignoreActors,
					setCycle			Walk,
					setPri			4,
					init,
				)
				(ego
					view				vLroom,
					illegalBits		0,
					posn				17 141,
					setLoop			lBowlingBall,
					setCycle			Forward,
					setStep			10 1,
					init,
				)
				(DaveScript changeState watchLarry)
				(RoomScript changeState cueTheKen)
			)
			((not (TstFlg beenIn350))
				(HandsOff)
				(SetFlg beenIn200)			;**	No more plot exposition.
				(SetFlg beenIn210)
				(SetFlg beenIn216)
				(SetFlg beenIn220)
				(SetFlg beenIn250)
				(SetFlg beenIn350)

				(= egoState GETTING_FIRED)
				(ego
					illegalBits		0,
					loop				facingWest,
					posn 				295 144,
					init,
				)
				(RoomScript changeState goSeeKen)
			)
			(else
				(NormalEgo facingWest)
				(ego
					observeControl cYellow,
					posn 				295 144,
					init,
				)
			)
		)
		(music
			number			sMuzak,
			loop				bgMusicLoops,
			play,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'sit')
				(Print "\"May I please sit down in one of these chairs?\"
					you ask Dave, the guard.")
				(Print "\"I'm sorry,\" says Dave.
					\"These chairs are for visitors only,
					not for former employees!")
			)

			((Said 'open/door')
				(Print "The door to the outside is never locked.")
				(Print "The door to Kenneth's office is ALWAYS locked!" LOW)
			)

			((Said 'drink, get/water')
				(Print "\"Say, Davie, may I have a drink of this water?\"")
				(Print "Dave responds,
					\"That water is for our beloved employees and not
					for incompetent former goldbricks!\"")
			)

			((Said 'take')
				(Print "\"Get your hands off that!\" cries the guard.")
			)

			((Said 'give')
				(Print "\"Don't try to bribe me, Laffer!\" replies Dave.")
			)

			((Said 'talk/man, guard')
				(switch (++ DaveTalkCounter)
					(1
						(Print "\"Hi, ya, Dave. How's it going!\"
							you attempt to be cheerful.
							\"May I go inside?\"")
						(Print "\"Absolutely not,\" replies Dave.
							\"I'd like to keep MY job here, Larry,
							not like SOME people I know!\"")
					)
					(2
						(Print "\"Say, Dave,\" you smile,
							\"have you heard that new piano player over at the
							casino?\"")
						(Print "\"Nope,\" he replies,
							\"but I've heard OF her!\"")
					)
					(else
						(Print "\"Hey, Dave,\" you say offhandedly,
							\"ya gettin' any lately?\"")
						(Print "\"Well, I made it with that new entertainer
							over at the casino lounge,\" he grins,
							\"but, that's no challenge.
							I hear every man on the island beat me to her!\"")
					)
				)
			)

			((or	(Said '//job')
					(Said '/job'))
				(Print "You wouldn't work for this firm again,
					even if they WOULD hire you!")
			)

			((or	(Said '/ken')
					(Said '//ken'))
				(Print "You wouldn't work for him again!")
				(Print "(At his request!)" LOW)
			)

			((Said 'look>')
				(cond
					((Said '/pussy, tiger, rug, (head<tiger), skin')
						(Print "This was the only tiger remaining on this island.")
						(Print "(Chairman Kenneth thought it would make a 
							perfect throw rug!)" LOW)
					)

					((Said '/chair, book')
						(Printf "Nothing here is of importance to you now, %s." 
							egoName)
					)

					((Said '/window')
						(Print "The view out the window looks much like the
							view immediately outside.")
					)

					((Said '/flower')
						(Print "Someone has reset your pins!")
					)

					((Said '/water, fountain, waterfall')
						(Print "Wow! Other offices just have water coolers.
							\"Natives, Inc.\" has a bubbler of Perrier
							built into the floor!")
					)

					((Said '/table')
						(Print "The table has four legs and rests on the floor.")
					)

					((Said '/guard, man')
						(Print "He's been behind that desk ever since you started
							working here.")
					)

					((Said '/door')
						(Print "The door to the outside is to the right.
							You remember vividly your last trip through the
							door to the left!")
					)

					((Said '[/room]')
						(Print "The reception area of \"Natives, Inc\05\"
							contains a few chairs, a tigerskin rug,
							and a guard behind a large desk.")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**	It's Larry's first time in here; tell him he's fired!

			(goSeeKen
				(ego
					setMotion		MoveTo 293 144 self,
				)
			)

			(nearDavesDesk
				(ego
					setMotion		MoveTo 281 144 self
				)
			)

			(goodMorning
				(Print "\"Good morning, David,\" you tell the guard.
					\"Another day, another dollar, eh?\"")
				(ego
					setMotion		MoveTo 225 166 self
				)
			)

			(maybeSo
				(Print "\"Perhaps so, Larry,\" Dave replies,
					\"but then again, perhaps not.
					Chairman Kenneth wants you to report to
					his office immediately.\"")
				(ego
					setMotion		MoveTo 123 166 self
				)
			)

			(okDave
				(Print "\"Ok, David,\" you respond.")
				(Print "But to yourself you think,
					\"Don't worry, it's probably nothing.
					I bet he wants to compliment me on my last big ad campaign.\"")
				(ego
					setPri			RELEASE,
					setMotion		MoveTo 53 138 self
				)
			)

			(atDoor
				(ego
					loop				facingWest,
				)
				(aDoor
					setCycle			EndLoop self,
				)
			)

			(doorOpenDone
				(aDoor	  			stopUpd)
				(ego
					setMotion		MoveTo -10 141
				)
			)

			(changeToKensOffice
				;**	edgeHit will change rooms here
			)


			;**	This handles the return from Ken's office

			(cueTheKen
				(ego
					setMotion		MoveTo 192 141 self,
				)
				(aKen
					setMotion		MoveTo 35 141
				)
				(soundFX
	 				number			sBowling,
					loop				1,
					play,
				)
			)

			(larryAtPins
				(ego
					setPri			10,
					setLoop			lUnwinding,
					cel				0,
					setCycle			EndLoop,
					setMotion		MoveTo 255 137 self
				)
				(aPins
					setCycle			EndLoop
				)
				(music				stop)
				(soundFX
					stop,
	 				number			sStrike,
					loop				1,
					play,
				)
			)

			(timing
				(soundFX				fade)
				(aPins				stopUpd)
				(ego					stopUpd)
				(delay 2)
			)

			(split
				(Printf "\"%s\" yells Chairman Kenneth,
					\"another 7-10 split!\"" expletive)
				(delay 2)
			)

			(leave
				(Print "\"Davie boy,\" barks Kenneth,
					\"load up my ramdisk;
					I'm gonna take the laptop out for a spin today!\"")
				(aKen
					setMotion		MoveTo 0 141 self
				)
			)

			(kenGone
				(aKen				dispose)
				(aDoor
					setCycle		BegLoop self,
				)
				(Print "Well, well, Larry.
					It seems your ad campaign wasn't the only thing
					Chairman Kenneth didn't appreciate!")
				(Print "Oh, well; you lived off your wits once,
					you can do it again...")
				(delay 3)
			)

			(doorSlam
				(soundFX
					number			sDoorClosing,
					loop				1 ,
					play ,			
				)
			)

			(larryLeaving
				(curRoom drawPic blackPicture IRISIN)
				(cast eachElementDo #hide)
				(= cycles 20)
			)

			(changeRooms
				(Print "You shake the haze from your brain,
					slowly rise to your feet and stumble groggily outside...")
				(= egoState GETTING_FIRED)
				(curRoom newRoom 305)
			)
		)
	)
)


(instance atpChair1 of PicView
	(properties
		view	 				vRoom
;		loop					lChairs
		cel					1
		x						69
		y						124
;		priority				8
	)
)
(instance atpChair2 of PicView
	(properties
		view	 				vRoom
;		loop					lChairs
		cel					2
		x						256
		y						126
;		priority				8
	)
)
(instance atpChair3 of PicView
	(properties
		view	 				vRoom
;		loop					lChairs
		cel					3
		x						295
		y						168
;		priority				12
	)
)
(instance atpChair4 of PicView
	(properties
		view	 				vRoom
;		loop					lChairs
;		cel					0
		x						25
		y						168
;		priority				12
	)
)
(instance atpTable1 of PicView
	(properties
		view	 				vRoom
;		loop					lChairs
		cel					4
		x						93
		y						108
;		priority				7
	)
)
(instance atpTable2 of PicView
	(properties
		view	 				vRoom
;		loop					lChairs
		cel					5
		x						232
		y						109
;		priority				7
	)
)

(instance aPins of Prop
	(properties
		view					vRoom
		loop					lOtherPins
;		cel					0
		x						222
		y						132
	)

	(method (init)
		(super 				init)
		(self
			setPri			9,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance atpTenPin of PicView
	(properties
		view					vRoom
		loop					lPins7&10
;		cel					cOnePin
		x						239
		y						148
		priority				11
		signal				ignrAct
	)
)

(instance aDave of Prop
	(properties
		view					vDave
		loop					lDturningHead
;		cel					0
		x						144
		y						169
	)

	(method (init)
		(super 				init)
		(self
			cycleSpeed		1,
			setPri			15,
			setScript		DaveScript,
			ignoreActors,
		)
	)
)
(instance DaveScript of Script
	(method (doit)
		(super doit)
		(if (== lDturningHead (aDave loop))
			(aDave cel
				(cond
					(( <  80 (ego x))	0)
					(( < 140 (ego x))	1)
					(( < 180 (ego x))	2)
					(( < 240 (ego x))	3)
					(else					4)
				)
			)
		)
	)

	(method (changeState newState &tmp DaveLoop)
		(switch (= state newState)

			(DaveInit
				(= cycles (Random 11 22))
			)

			(DaveStart
				(delay (Random 3 9))
				(switch (= DaveLoop (Random 0 5))
					(0
						(aDave
							loop			lDscratchHead,
							setCycle		EndLoop self,
						)
						(delay 0)
					)
					(1
						(aDave
							loop			lDleanBack,
							setCycle		EndLoop ,
						)
					)
					(else
						(aDave
							loop			lDturningHead,
						)
						(= state readyToStartOver)
					)
				)
			)

			(itsDone
				(aDave
					setCycle		BegLoop self
				)
				(= state readyToStartOver)
			)

			(watchLarry
				(aDave
					loop			lDturningHead,
				)
				(= cycles 0)
				(delay 0)
			)
		)
	)
)

(instance aKen of Actor
	(properties
		view					vKen
		x						17
		y						141
		illegalBits			0
	)
)

(instance aWaterfall of Prop
	(properties
		view					vRoom
		loop					lWaterfall
		x						163
		y						103
	)
)

(instance aDoor of Prop
	(properties
		view					vRoom
		loop					lDoor
		x						29
		y						94
	)

	(method (init)
		(super init)
		(self
			setCel			(if (== prevRoomNum 355) LastCel else 0),
			ignoreActors,
			stopUpd,
		)
	)
)

