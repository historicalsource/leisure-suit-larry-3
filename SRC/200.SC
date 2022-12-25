;**
;**	Logics for room 200 -- Vista Point
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 28, 1989
;**


(script#						200)

(public
	rm200				0
)


(procedure
	PrintPlot
)


(define vRoom							200)
	(define	lBinoculars			0)
		(define	cBinocular1	0)
		(define	cBinocular2	1)

(define	vProducedBy					201)
	(define	lProducedBy			0)
	(define	lAl					1)

(define	nearPlaque			(& (ego onControl) cBlue))
(define	nearBinocular1		(& (ego onControl) cGreen))
(define	nearBinocular2		(& (ego onControl) cCyan))


(enum												;**	RoomScript state values
	intro
	learnThePlot
	readMsg2
	plotDone
	demo1
	demo2
	demo3
)


(enum												;**	CreditsScript state values
	doCredits
	doCredits1
	doCredits2
	doCredits3
	clearCredits
)


(local
	seenMsg
	[string 222]
)


(instance rm200 of Room
	(properties
		picture		200
		east			210
		south			210
	)

	(method (init)
		(Load VIEW vRoom)

		(super init)

		(self
			setScript 		RoomScript
		)

		(if (and	(TstFlg beenIn200)
					(not (TstFlg sawCredits200)))
			(Load VIEW vProducedBy)
			(aCredit1			init)
			(aCredit2			init)
		)

		(addToPics
			add 				atpBinocular1,
			add 				atpBinocular2,
			doit
		)

		(if (and (!= prevRoomNum 203)		;**	If he didn't just read plaque...
					(!= prevRoomNum 206))	;**	and he didn't use binoculars...
			(ego									;**	he walked in, or restarted in.
				posn 			315 167,
				loop			facingWest,
			)
		)
		(NormalEgo)
		(ego			init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event)
		(if (and (not (super handleEvent event))
					;**	(not (event claimed))
					modelessDialog
					(== (event message) ENTER)
					(== (event type) keyDown))
			(event claimed TRUE)
			(cls)
			(self cue)
		)

;**			(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said '/telescope, binocular>')
				(cond
					((Said 'get')
						(Print "The binoculars are here for the enjoyment of all.
							Please respect the wishes of \"Natives, Inc.\" and
							allow them to remain.")
					)

					((Said 'use, (look<through, in)')
						(cond
							(egoIsPatti
								(Print "You won't find Larry by using these binoculars.")
							)
							((and (not nearBinocular1)
									(not nearBinocular2))
								(NotClose)
							)
							(nearBinocular2
								(Print "These binoculars are broken.")
							)
							((TstFlg beenIn206)
								(Print "One look at that is all your heart can take!")
							)
							(else
								(Ok)
								(curRoom newRoom 206)
							)
						)
					)

					((Said 'look')
						(Print "Two pairs of free binoculars are mounted on posts
							near the fence.")
					)
				)
			)

			((Said 'get/plaque')
				(Print "The plaque is securely screwed.")
				(Print "(And so are you if you want to take it!)" LOW)
			)


			((or	(Said 'look/sky')
					(Said 'up<look'))
				(Print "From high up here on Vista Point,
					you are almost above the smell of the city.")
			)

			((Said 'look>')
				(cond
					((Said '/sky, up')
						(Print "From high up here on Vista Point,
							you are almost above the smell of the city.")
					)

					((Said '/mountain, land, cliff')
						(Print "From here, you can see Kalalau's extensive 
							land holdings high in that mountain valley across
							the island.")
					)

					((Said '/fence, rail')
						(Print "The fence is there to prevent clumsy people
							from falling over the edge and ruining the
							barbecues of the villagers below.")
					)

					((Said '/cliff, edge')
						(Print "This mesa has extremely steep cliffs on three
							sides.")
					)

					((Said '/ocean, beach, point, lagoon, bay')
						(Print "The polluted lagoon stretches off into the
							distance like a bag of cold fast-food fries.")
					)

					((Said '/city, down, building, casino, hotel, trap')
						(Print "The city below you stretches from the edge of the
							lagoon almost up to your current vantage point.
							The large resort hotels stretch before you like
							billboards on the highway of the damned.")
						(if (<= filthLevel CLEAN)
							(Print "(Oops, sorry. 
								That should have been \"highway of the darned!\"
								You're playing \"clean mode,\" aren't you?)")
						)
					)

					((Said '/grass, ground, sidewalk')
						(Print "Vista Point has a well-manicured, bluegrass lawn
							with a bronze plaque.")
					)

					((Said '/gull')
						(Print "Where?")
					)

					((Said '/plaque')
						(if nearPlaque
							(if (not (TstFlg beenIn203))
								(SetFlg beenIn203)
								(theGame changeScore 2)
							)
							(Ok)
							(curRoom newRoom 203)
						else
							(NotClose)
						)
					)

					((Said '[/view, room]')
						(Print "From here, on the lovely Vista Point,
							high up the slopes of Nontoonyt Volcano,
							the City lies spread below your feet like the
							floor of a movie theatre after a Saturday matinee!")
						(Print "A lovely lawn with a bronze plaque lies near
							two pair of binoculars.")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(if (not (TstFlg beenIn200))
					(delay 5)
				)
;**					(if demo
;**						(TheMenuBar draw)
;**						(StatusLine	enable)
;**						(delay 1)
;**					)
			)

			(learnThePlot
				(Format @string "My, how this island has changed!
					You remember when you parachuted down here
					there was nothing but a sleepy little native village
					inhabited by a tribe of illiterate islanders.
					Now look at it:
					it's overgrown with hotels, resorts, casinos, and
					cheap tourist traps.")	
				(= seconds (PrintPlot))
;**					(if demo
;**						(ego
;**							setMotion		MoveTo 145 78,
;**						)
;**						(= state (- demo1 1))
;**						(-= seconds 4)				
;**					)
			)

			(readMsg2
				(SetFlg beenIn200)
				(if (not (TstFlg beenIn216))
					(Format @string 
						"Such is the cost of progress.
						At least your job as Vice-President of Marketing
						for \"Natives, Inc.\" has enabled you to provide
						a lovely home for you and your beloved wife, Kalalau."
					)	
					(= seconds (PrintPlot))
				)	
			)

			(plotDone
				(delay 0)
			)


;**				(demo1
;**					(if demo
;**						(Format @string 
;**							"Sounds like fun, eh?
;**							Why don't we go check out some of the island's hot spots
;**							right now..."
;**						)	
;**						(= seconds (PrintPlot))
;**					)
;**				)
;**	
;**				(demo2
;**					(if demo
;**						(cls)
;**						(= creditsFinished $FFFF)	
;**						(curRoom newRoom 260)
;**					)
;**				)
;**	
;**				(demo3
;**					(cls)
;**					(= creditsFinished $FFFF)	
;**					(curRoom newRoom 260)
;**				)
		)
	)
)


(instance atpBinocular1 of PicView
	(properties
		view				vRoom
;		loop				lBinoculars
;		cel				cBinocular1
		x	 				137
		y					82
		signal			ignrAct
	)
)
(instance atpBinocular2 of PicView
	(properties
		view				vRoom
;		loop				lBinoculars
		cel				cBinocular2
		x	 				205
		y					79
		signal			ignrAct
	)
)

(instance aCredit1 of Prop
	(properties
		view					vProducedBy
;		loop					lProducedBy
;		cel					0
		x						288
		y						131
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
		)
	)
)
(instance aCredit2 of Prop
	(properties
		view					vProducedBy
		loop					lAl
;		cel					0
		x						288
		y						154
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
			setScript		CreditsScript,
		)
	)
)

(instance CreditsScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(doCredits
				(delay 4)
			)

			(doCredits1
				(aCredit1 setCycle EndLoop)
				(= cycles 16)
			)

			(doCredits2
				(aCredit2 setCycle EndLoop)
				(= cycles 22)
			)

			(doCredits3
				(SetFlg sawCredits200)
				(aCredit1 setCycle BegLoop)
				(aCredit2 setCycle BegLoop self)
			)

			(clearCredits
				(aCredit1 dispose)
				(aCredit2 dispose)
			)
		)
	)
)
(procedure (PrintPlot &tmp t )
	(Print @string
		#at 			10 5 
		#width 		290
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 3 t))
)
