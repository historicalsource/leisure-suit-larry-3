;**
;**	Logics for room 210 -- a little jungle path with some granadilla wood
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 20, 1989
;**


(script#				210)

(public
	rm210				0
)


(procedure
	PrintPlot
)


(define	vRoom							210)
	(define	lStick				0)

(define	vDesignedBy					212)
	(define	lDesignedBy			0)
	(define	lAl					1)

;(define	vLstooping					709)
;	(define	stoopingOffsetX	19)


(define	nearWood			(& (ego onControl) cBrown))


(enum												;**	RoomScript state values
	intro
	learnThePlot
	readMsg2
	plotDone
	getIt
	atIt
	DrBenDover
	getDone
)


(enum												;**	CreditsScript state values
	doCredits
	doCredits1
	doCredits2
	doCredits3
	clearCredits
)


(local
	[string 200]
)


(instance rm210 of Room
	(properties
		picture		210
		south			220
	)

	(method (init)
		(super init)

		(self
			setScript 		RoomScript
		)

		(cond
			((not (TstFlg beenIn210))
				(Load VIEW vPointer)
			)
			((not (TstFlg beenIn216))
				(Load VIEW vPointer)
			)
			((not (TstFlg sawCredits210))
				(Load VIEW vDesignedBy)
				(aCredit1			init)
				(aCredit2			init)
			)
		)

		(if (InRoom iWood)
			(Load VIEW vLstooping)
			(aWood init)
		)

		(cond
			((== prevRoomNum 200)
				(ego
					posn			317 126,
					loop			facingWest
				)
			)
			((== prevRoomNum 216)			(ego posn	2 163))
			((== prevRoomNum 213)			(ego posn	2 175))
			(else									(ego posn 317 175))
		)

		(NormalEgo)
		(ego					init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (ego edgeHit)
			(cond
				((& (ego onControl) cCyan)		(curRoom newRoom 200))
				((& (ego onControl) cGreen)	(curRoom newRoom 216))
				((& (ego onControl) cRed)		(curRoom newRoom 220))
				((& (ego onControl) cBlue)		(curRoom newRoom 213))
			)
		)
	)


	(method (handleEvent event &tmp i)
		(if (and (not (super handleEvent: event))
					(not (event claimed))
					modelessDialog
					(== (event message) ENTER)
					(== (event type) keyDown))
			(event claimed TRUE)
			(cls)
			(self cue)
		)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(if (Said 'look>')
			(if (Said '/tree')
				(Printf "The granadilla is short and graceful,
					with a gray trunk, and delicately spreading branches.%s"
					(if (InRoom iWood)
						{ Beneath its outstretched boughs lies a beautiful
						piece of wood, probably cut by a native then forgotten.}
					else
						{}
					)
				)
			)

			(if (and (InRoom iWood)
						(Said '/stick, wood'))
				(Print "Beneath the granadilla lies a beautiful piece of wood.")
			)

			(if (Said '[/room]')
				(Print "The native corporation (Natives, Inc.) has done an
					excellent job of preserving the environment of the jungle,
					at least in this location!
					A beautiful, specimen granadilla tree grows here.")
			)
		)

		(if (Said 'get/wood')
			(cond
				((!= egoState NORMAL)
					(NotNow)
				)
				((not (InRoom iWood))
					(Print "There is no wood lying around here.")
				)
				((not nearWood)
					(Print "Move over until you are beside the small tree
						with the gray trunk in the lower right-hand corner
						of the scene.")
				)
				(else
					(self changeState getIt)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(cond
					((not (TstFlg beenIn210))
						(= cycles 30)
					)
					((not (TstFlg beenIn216))
						(= cycles 20)
						(++ state)
					)
				)
			)

			(learnThePlot
				(Format @string 
					"The villagers were wise to retain total financial
					and political control of their island.
					For example, they've never allowed vehicular traffic anywhere.
					Everyone walks wherever they go."
				)	
				(= seconds (PrintPlot))
			)

			(readMsg2
				(SetFlg beenIn210)
				(if (not (TstFlg beenIn216))
					(aCredit1
						view				vPointer,
						posn				0 121,
						setCycle			Forward,
						cycleSpeed		1,
						ignoreActors,
						init,
						setPri			12,
					)
					(Format @string 
						"Up this path lies your home.
						Your beloved Kalalau is probably there right now,
						waiting patiently for your return...."
					)	
					(= seconds (PrintPlot))
				)
			)

			(plotDone
				(aCredit1 dispose)
				(delay 0)
			)


			;**	Pick up the Wood

			(getIt
				(delay 0)
				(= cycles 0)
				(Ok)
				(HandsOff)
				(if (< (ego x) (aWood x))
					(ego 
						illegalBits	0,
						setMotion	MoveTo (- (aWood x) stoopingOffsetX) (ego y) self,
					)
				else
					(ego 
						illegalBits	0,
						setMotion:	MoveTo (+ (aWood x) stoopingOffsetX) (ego y) self,
					)
				)
			)

			(atIt
				(ego
					view				vLstooping,
					loop				(> (ego x) (aWood x)),
					cel				0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(DrBenDover
				(aWood 			hide)
				(ego
					get 			iWood,
					setCycle		BegLoop self,
				)
			)

			(getDone
				(NormalEgo)
				(theGame changeScore 2)
				(Print "You take the beautiful chunk of granadilla wood
					from beneath the tree.")
			)
		)
	)
)


(instance aWood of View
	(properties
		view			vRoom
		x				223
		y				161
	)
)

(instance aCredit1 of Prop
	(properties
		view					vDesignedBy
;		loop					lDesignedBy
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
		view					vDesignedBy
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
				(SetFlg sawCredits210)
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

