;**
;**	Logics for room 390 -- Aerobics Studio
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 26, 1989
;**


(script#				390)

(public
	rm390				0
)


(procedure
	PrintBambi		1
	PrintLarry		2
)


(define	vRoom							390)
	(define	lLens					0)
	(define	lMike					1)
	(define	lSpeakerLeft		2)
	(define	lSpeakerRight		3)

(define	vBambiBigMild				391)
	(define	lJumpingJacks		0)
	(define	lSquats				1)
	(define	lSplitsEL			2)
	(define	lSitStretch			3)
	(define	lStand&TouchFloor	4)
(define	vBambiBigHot				392)
	(define	lBendOver			0)
	(define	lMoveAss				1)
	(define	lSitOnItEL			2)
	(define	lSit&Move			3)
	(define	lGoingDown			4)
	(define	lChangeToKneesEL	5)
	(define	lReallyMoveAss		6)

;(define	vBambi						393)	

(define	vMonitorMild				396)	;**		These two are the two big 
(define	vMonitorHot					397)	;**	views sized to -86%.


(define	sAerobics					390)


(define nearBambi					(& (ego onControl) cCyan))


(enum												;**	BambiScript state values
	bambiInit
	doMildAerobics
	standAround
	bambiDone
	myNameIs
	changeRooms
	clearOfTV
)


(enum												;**	RoomScript state values
	intro
	goForIt
	killThatMuzak
	showtime
	jumpingJacks
	squats
	splitsEL
	sitStretch
	splitsBL
	stand&TouchFloor
	bendOver
	howAbout
	youreGettingThere
	mikeErection
	sitOnItEL
	sit&Move
	goingDown
	changeToKneesEL
	reallyMoveAss
	burnUp
	jumpOffStage
	waitForMusicToEnd
	leaveTheRoom
	okByMe
	LatTV
	pastTV
)


(local
	seenMsg
	[string 200]
)


(synonyms
	(woman dancer bambi )
)


(instance rm390 of Room
	(properties
		picture		390
	)

	(method (init)
		(aMike				init)

		(super init)

		(if (and (not (TstFlg hadBambi))
					(not (TstFlg missedBambi)))
			(aMonitorRight		init)
			(aMonitorLeft		init)
			(aBambi 				init)
		)

		(self
			setScript 			RoomScript
		)

		(cond
			((== prevRoomNum 395)
				(ego
					posn		135 155 ,
					loop		facingSouth,
				)
				(if (!= egoState DOING_IT)
					(= egoState NORMAL)					
				else
					(music stop)
					(Load VIEW vBambiBigHot)
					(Load VIEW vBambi)
					(Load VIEW vMonitorHot)
					(Load SOUND sAerobics)
					(aBambi
						setScript		FALSE,
					)
					(RoomScript changeState goForIt)
				)
			)
			(else
				(ego
					posn 			243 134,
					setLoop		facingLeft,
				)
			)
		)

		(if (!= egoState DOING_IT)
			(NormalEgo)
			(if (cast contains aBambi)
				(BambiScript changeState doMildAerobics)				
			)
		)
		(ego					init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if ( < (ego priority) 5)
			(if (not seenMsg)
				(= seenMsg TRUE)
				(ShakeScreen 1 SHAKE_UP_DOWN)
				(Print "\"Jeez, it's dark back here!\" you cry,
					ramming your nose into the rear wall of the studio.
					\"OW!\"")
				(ego setMotion FALSE)
			)
		else
			(= seenMsg FALSE)
		)

		(if (& (ego onControl) cBlue)
			(curRoom newRoom 360)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'stand, get, jump, climb<on/stage')
				(Print "Stay on the floor.")
			)

			((Said 'get')
				(Print "There is nothing in the studio that you need.")
			)

			((Said 'turn<on/tv')
				(Print "Leave the equipment alone!")
			)

			((Said 'make/video')
				(Print "Who would want a video of you?")
			)

			((or	(Said '/workout, (work<out), aerobic')
					(Said '//workout, (work<out), aerobic')
					(Said 'exercise, dance, strip, undress, (work<out), workout'))
				(if egoIsHunk
					(Print "You've done enough exercising for one game!")
				else
					(Print "You need to, but this is not the place.
						Try the weight room, tubby!")
				)
			)

			((Said 'look>')
				(cond
					((Said '/tv, equipment, camera, video, microphone')
						(Print "The room is filled with enough professional video
							equipment to produce a complete movie.")
					)

					((Said '/light')
						(Print "The glare is frightening.")
					)

					((Said '[/room]')
						(Printf "You are in Fat City's Aerobics Studio, 
							which is presently filled with audio/video equipment. %s"
							(cond
								((cast contains aBambi)
									{A beautiful blond woman stands on the stage with
										a puzzled expression on her face.}
								)
								((TstFlg missedBambi)
									{It looks like someone was recently here making
										a video.}
								)
								(else
									{The equipment seems to have suffered some sort of 
										meltdown!}
								)	
							)
						)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
			)

			
			;**	it's showtime

			(goForIt
				(HandsOff)
				(music stop)
				(= cycles 2)
				(delay 3)
			)

			(killThatMuzak						
				(music stop)					;**	SHUT THE FUCK UP!
			)

			(showtime
				(music stop)
				(= oldGameSpeed (theGame setSpeed 6))
				(Print "\"Tell me what you think of these moves, Larry,\" 
					Bambi says.")
				(music
					number			sAerobics,
					loop				FOREVER,
					play				self,
				)
				(aSpeakerLeft		init)
				(aSpeakerRight		init)
				(aBambi
					cycleSpeed		1,
					setCycle			Forward,
				)
				(= cycles 30)
			)

			(jumpingJacks
				(aBambi
					setLoop			lJumpingJacks,
				)
				(= cycles 30)
			)

			(squats
				(aBambi
					setLoop			lSquats,
				)
				(= cycles 30)
			)

			(splitsEL
				(aBambi
					setLoop			lSplitsEL,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(sitStretch
				(aBambi
					setLoop			lSitStretch,
					setCycle			Forward,
				)
				(= cycles 30)
			)

			(splitsBL
				(aBambi
					setLoop			lSplitsEL,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(stand&TouchFloor
				(aBambi
					setLoop			lStand&TouchFloor,
					setCycle			Forward,
				)
				(= cycles 30)
			)

			(bendOver
				(Format @string "\"It's needs to be a little hotter, Bambi,\" 
					you say. \"Come on, honey, really turn me on!\"")
				(PrintLarry)
				(aBambi
					view				vBambiBigHot,
					setLoop			lBendOver,
					cycleSpeed		0,
				)
				(= cycles 30)
			)

			(howAbout
				(Format @string "\"How about this?\"")
				(PrintBambi)
				(aBambi
					setLoop			lMoveAss,
				)
				(= cycles 30)
			)

			(youreGettingThere
				(Format @string "\"You're getting there!\"")
				(PrintLarry)
				(= cycles 30)
			)

			(mikeErection
				(aMike
					setCycle			EndLoop ,
				)
				(= cycles 30)
			)

			(sitOnItEL
				(aMike				stopUpd)
				(aBambi
					setLoop			lSitOnItEL,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(sit&Move
				(aBambi
					setLoop			lSit&Move,
					setCycle			Forward,
				)
				(= cycles 30)
			)

			(goingDown
				(Format @string "\"Go, Bambi, GO!\"")
				(PrintLarry)
				(aBambi
					setLoop			lGoingDown,
				)
				(= cycles 30)
			)

			(changeToKneesEL
				(aBambi
					setLoop			lChangeToKneesEL,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(reallyMoveAss
				(Format @string "\"Here's a little move that could put us 
					`over the top!'\" Bambi yells.")
				(PrintBambi)
				(aBambi
					setLoop			lReallyMoveAss,
					setCycle			Forward,
				)
				(= cycles 30)
			)

			(burnUp
				(aMonitorLeft		dispose)
				(aMonitorRight		dispose)
				(aLens
					init,
					setCycle			EndLoop self,
				)
			)

			(jumpOffStage
				(aLens	 			stopUpd)
				(delay 5)						;**	just in case we looped the music
			)

			(waitForMusicToEnd
				(delay 0)
				(Format @string "\"I can't stand this any longer, Larry!
					All these movements have turned ME on!
					Come on, Larry! 
					Enough of this rehearsal.
					IT'S SHOW TIME!!\"")
				(PrintBambi)
				(music fade)
				(aBambi
					view				vBambi,
					setPri			RELEASE,
					setLoop			facingEast,
					posn				157 155,
					setCycle			Walk,
					setMotion		MoveTo 180 154 self,
				)
			)

			(leaveTheRoom
				(aBambi
					setMotion		MoveTo 200 134 self,
				)
			)

			(okByMe
				(ego
					setCycle			Walk,
					setLoop			RELEASE,
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo 180 154 self,
				)
				(aBambi
					setScript		BambiScript,
				)
				(BambiScript changeState clearOfTV)
				(theGame setSpeed oldGameSpeed)
			)

			(LatTV	
				(ego
					setMotion		MoveTo 200 134 self,
				)
			)

			(pastTV
				(ego
					setMotion		MoveTo 333 134,
				)
			)

			;**	changingRooms is taken care of by L hitting edge.
		)
	)
)


(instance aBambi of Actor
	(properties
		view					vBambiBigMild
		x						160
		y						155
	)

	(method (init)
		(super init)
		(self
			illegalBits		0,
			ignoreHorizon,
			ignoreActors,
			setPri			15,
			setScript		BambiScript,
		)
	)
)
(instance BambiScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look/girl')
				(cond
					((not nearBambi)
						(Print "A tall statuesque blond is working out on a stage
							surrounded by video equipment.")
					)
					((TstFlg needsShower)
						(Print "\"Phew!\" she cries,
							\"we DO have showers here, you know!
							I suggest you try one before I pass out!\"")
					)
					((TstFlg needsSoap)
						(Print "\"Phew! Have you ever tried using soap?
							It wouldn't kill you to practice a little 
							self-hygiene.\"")
							;**	\"hygiene\" spell-check September 21, 1989
					)
					((TstFlg needsDeodorant)
						(Print "\"Phew!\" squeeks Bambi. \"What B.O. you have!\"")
					)
					(else
						(self changeState myNameIs)
					)
				)
			)

			((Said '/tit, body')
				(Print "What a body!")
				(Print "(You vow to get more exercise in the future!)" LOW)
			)

			((Said '/girl')
				(if (not nearBambi)
					(NotClose)
				else
					(Print "Perhaps you should look at her first.")
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 2 green)

		(switch (= state newState)

			(bambiInit
			)

			(doMildAerobics
				(aBambi
					setCycle			Forward,
					setLoop			(switch (Random 0 2)
											(0 	lJumpingJacks)
											(1		lSquats)
											(else	lStand&TouchFloor)
										),
				)
				(delay (Random 5 11))
			)

			(standAround
				(aBambi
					setLoop		lSquats,
					setCel		0,
				)
				(delay (Random 2 4))
				(= state (- doMildAerobics 1))	;**	do something else soon
			)

			(bambiDone
			)


			;**	change to big face

			(myNameIs
				(aBambi
					setLoop		lSquats,
					setCel		0,
				)
				(if (TstFlg seenBambi)
					(Print "\"I'm back, Bambi!\"")
				else
					(Printf "\"Hello, beautiful,\" you say, %s" laffer)
				)
				(delay 3)
			)

			(changeRooms
				(if (TstFlg seenBambi)
					(Print "\"Hi, Larry. How's it going?\"")
				else
					(SetFlg seenBambi)
					(if egoIsHunk
						(Print "\"Wow, Larry! How come I've never seen you before?
							A hunk like you would be hard to forget!\"")
					else
						(Print "\"It's nice to meet you, Larry Laffer. 
							From the looks of that stomach,
							you've come to the right place!\"")
					)
				)
				(= egoState GOING_TO_BIG_FACE)
				(curRoom newRoom 395)
			)


			;**		This is where she leaps off the stage, & RoomScript makes
			;**	Larry follow her out to the tanning booth.

			(clearOfTV
				(aBambi
					setMotion		MoveTo 333 134,
				)
			)
		)
	)
)


(instance aSpeakerLeft of Prop
	(properties
		view					vRoom
		loop					lSpeakerLeft
		cel					1
		x						36 
		y						154
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			setCycle			Forward ,
		)
	)
)
(instance aSpeakerRight of Prop
	(properties
		view					vRoom
		loop					lSpeakerRight
		cel					1
		x						294 
		y						153
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			setCycle			Forward ,
		)
	)
)
(instance aLens of Prop
	(properties
		view					vRoom
		loop					lLens
;		cel					cLens
		x						60 
		y						73
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setPri			9,
			ignoreActors,
		)
	)
)
(instance aMike of Prop
	(properties
		view					vRoom
		loop					lMike
;		cel					0
		x						168
		y						61
		cycleSpeed			2
	)

	(method (init)
		(super init)
		(self
			setPri			2,
			stopUpd,
		)
	)
)
(instance aMonitorLeft of Prop
	(properties
		view					vMonitorMild
;		loop					0
;		cel					0
		x						93 
		y						121
	)

	(method (init)
		(super init)
		(self
			setPri			11,
			ignoreActors,
		)
	)

	(method (doit)
		(self
			view				(+ 5 (aBambi view)),
			loop				(aBambi loop),
			cel				(aBambi cel)
		)
		(super doit)
	)
)
(instance aMonitorRight of Prop
	(properties
		view					vMonitorMild
;		loop					0
;		cel					0
		x						229 
		y						121
	)

	(method (init)
		(super init)
		(self
			setPri			11,
			setCycle			Forward ,
			ignoreActors,
		)
	)

	(method (doit)
		(self
			view				(+ 5 (aBambi view)),
			loop				(aBambi loop),
			cel				(aBambi cel)
		)
		(super doit)
	)
)

(procedure (PrintBambi)
	(cls)
	(Print @string
		#at 			10 10
		#title		{Bambi says...}
		#width 		140
		#mode			teJustCenter
		#dispose
	)
)
(procedure (PrintLarry)
	(cls)
	(Print @string
		#at 			160 10
		#title		{You say...}
		#width 		140
		#mode			teJustCenter
		#dispose
	)
)
